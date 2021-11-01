package com.example.warehouset.main.home

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.warehouset.R
import com.example.warehouset.core.ResourceState
import com.example.warehouset.core.extentions.onClick
import com.example.warehouset.core.extentions.visibility
import com.example.warehouset.data.PostTransaction
import com.example.warehouset.databinding.FragmentHomeBinding
import com.example.warehouset.main.home.basket.Basket
import com.example.warehouset.main.home.basket.BasketDialog
import com.example.warehouset.main.home.detail.DetailDialogFragment
import com.example.warehouset.settings.Settings
import com.example.warehouset.settings.Settings.Companion.HEADERACCEPT
import com.example.warehouset.settings.Settings.Companion.HEADERXRW
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment: Fragment(R.layout.fragment_home) {
    private lateinit var navController: NavController
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val settings: Settings by inject()
    private val viewModel: HomeViewModel by viewModel()
    private val adapter: HomeAdapter by inject()
    private val basket =  Basket()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding.apply {
            rvProduct.adapter = adapter
            rvProduct.addItemDecoration(
                DividerItemDecoration(requireContext(),
                    DividerItemDecoration.VERTICAL)
            )
            etSearch.isFocusable = false
            etSearch.isFocusableInTouchMode = false
            etSearch.onClick {
                etSearch.isFocusable = true
                etSearch.isFocusableInTouchMode = true
            }
            adapter.onClickedSmallImageProduct { productName, productImage ->
                val dialog = DetailDialogFragment(requireContext())
                dialog.imageUrl = productImage
                dialog.productName = productName
                dialog.show()
            }
            adapter.onClickedEditText {
                etSearch.isFocusable = false
                etSearch.isFocusableInTouchMode = false
            }
            adapter.editTextChanged { quantity, model,pos ->
                adapter.models[pos].quantity = quantity
                basket.setProduct(model,quantity)
                adapter.notifyDataSetChanged()
                etSearch.isFocusable = false
                etSearch.isFocusableInTouchMode = false
            }

            etSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    p0?.let {
                        if (it.isEmpty()) {
                            viewModel.getProducts(HEADERXRW, HEADERACCEPT, "Bearer ${settings.token}")
                        } else {
                            //page = 1
                            viewModel.searchByNameProductAndBarCodeProduct(HEADERXRW, HEADERACCEPT, "Bearer ${settings.token}",it.toString())
                        }
                    }
                }
            })

            floatingButton.onClick {
                val dialog = BasketDialog(requireContext())
                dialog.list = basket.product.toMutableList()
                dialog.show()
                dialog.onClickOrders { products->
                    viewModel.postProducts(HEADERXRW,HEADERACCEPT,"Bearer ${settings.token}", PostTransaction(products))
                }
            }
            viewModel.getProducts(HEADERXRW, HEADERACCEPT, "Bearer ${settings.token}")
        }
        setupObserver()
    }

    private fun setupObserver(){
        binding.apply {
            viewModel.products.observe(viewLifecycleOwner, {
                when(it.status) {
                    ResourceState.LOADING ->{progressBar.visibility(true)}
                    ResourceState.SUCCESS->{progressBar.visibility(false)
                        it.data?.let { data->
                            adapter.models = data.payload.toMutableList()}
                        }
                    ResourceState.ERROR->{progressBar.visibility(false)
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()}
                }
            })
            viewModel.postProducts.observe(viewLifecycleOwner,{
                when(it.status) {
                    ResourceState.LOADING ->{progressBar.visibility(true)}
                    ResourceState.SUCCESS->{progressBar.visibility(false)
                        val dialog = AlertDialog.Builder(requireContext())
                        dialog.setTitle("УСПЕШНО!!!")
                        dialog.setMessage(it.data!!.message)
                        dialog.show()
                    }
                    ResourceState.ERROR->{progressBar.visibility(false)
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()}
                }
            })
            viewModel.productSearch.observe(viewLifecycleOwner,{
                when(it.status){
                    ResourceState.LOADING->{progressBar.visibility(true)}
                    ResourceState.SUCCESS-> {
                        progressBar.visibility(false)
                        it.data.let { data ->
                            if (data!!.successful) {
                                if (data.payload.isEmpty()){
                                    adapter.models = mutableListOf()
                                }else{
                                    adapter.models = data.payload.filter { i ->
                                        i.productName.lowercase().contains(
                                            binding.etSearch.text.toString().lowercase()
                                        ) || i.barCode.let { code ->
                                            code!!.contains(binding.etSearch.text.toString())
                                        }
                                    }.toMutableList()
                                }
                            } else {
                                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                    ResourceState.ERROR->{progressBar.visibility(false)
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()}
                }
            })
        }
    }
}