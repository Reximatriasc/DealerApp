package com.example.dealerapp.ui
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.dealerapp.application.DealerApplication
import com.example.dealerapp.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private lateinit var applicationContext: Context
    private val dealerViewModel: DealerappViewModel by viewModels {
        DealerViewModelFactory((applicationContext as DealerApplication).repository)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = DealerListAdapter { Dealer ->
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(Dealer)
            findNavController().navigate(action)
        }

//        binding.dataRecyclerView.adapter = adapter
 //       binding.dataRecyclerView.layoutManager = LinearLayoutManager(context)
 //       dealerViewModel.allDealer.observe(viewLifecycleOwner) { Dealer ->
 //          Dealer.let {
//                if (Dealer.isEmpty()) {
//                    binding.emptyTextView.visibility = View.VISIBLE
//                    binding.illustrationImageView.visibility = View.VISIBLE
//                }else {
//                    binding.emptyTextView.visibility = View.GONE
 //                   binding.illustrationImageView.visibility = View.GONE
//                }
//                adapter.submitList(Dealer)
//            }
//        }

       binding.addFAB.setOnClickListener {
         val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(null)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}