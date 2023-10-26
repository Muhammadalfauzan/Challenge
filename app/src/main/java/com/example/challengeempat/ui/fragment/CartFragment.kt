package com.example.challengeempat.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengeempat.R
import com.example.challengeempat.ViewModelFactory
import com.example.challengeempat.adapter.CartAdapter
import com.example.challengeempat.databinding.FragmentCartBinding
import com.example.challengeempat.viewmodel.CartViewModel

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var cartViewModel: CartViewModel
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        setUpCartViewModel()

        cartAdapter = CartAdapter(cartViewModel)
        binding.rvCart.setHasFixedSize(true)
        binding.rvCart.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCart.adapter = cartAdapter

        cartViewModel.cartItems.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.ivCartKosong.visibility = View.VISIBLE
                binding.rvCart.visibility = View.GONE
            } else {
                binding.ivCartKosong.visibility = View.GONE
                binding.rvCart.visibility = View.VISIBLE
                cartAdapter.updateDataCart(it)
            }
        }

        cartViewModel.totalPrice.observe(viewLifecycleOwner) {
            binding.txtTotalharga.text = it.toString()
        }

        orderItem()

        return binding.root
    }

    private fun setUpCartViewModel() {
        val viewModelFactory = ViewModelFactory(requireActivity().application)
        cartViewModel = ViewModelProvider(this, viewModelFactory)[CartViewModel::class.java]
    }

    private fun orderItem() {

        binding.btnPesan.setOnClickListener {
            if (!cartViewModel.cartItems.value.isNullOrEmpty()) {
                findNavController().navigate(R.id.action_cartFragment_to_konfirmasiFragment)
            } else {
                Toast.makeText(requireContext(), "Your cart is empty", Toast.LENGTH_SHORT).show()
            }
        }

    }

    /* private fun showNoteDialog(newItem: CartData) {
         val noteEditText = EditText(requireContext())

         AlertDialog.Builder(requireContext())
             .setTitle("Add Note")
             .setView(noteEditText)
             .setPositiveButton("OK") { _, _ ->
                 val note = noteEditText.text.toString()
                 if (note.isNotEmpty()) {
                     newItem.note = note
                     // Gunakan metode addCartToUpdate untuk menambahkan item atau memperbarui item yang sudah ada
                     cartViewModel.addCartToUpdate(newItem)
                 }
             }
             .setNegativeButton("Cancel") { dialog, _ ->
                 dialog.dismiss()
             }
             .show()
     }*/
}
