package com.example.android.recklinetest.game.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.android.recklinetest.databinding.FragmentResultGameBinding


class ResultGameFragment : Fragment() {

    private lateinit var binding: FragmentResultGameBinding
    private val viewModel by viewModels<ResultGameViewModel> {
        ResultGameViewModelFactory(
            ResultGameFragmentArgs.fromBundle(requireArguments()).correctAnswers,
            ResultGameFragmentArgs.fromBundle(requireArguments()).totalQuestions,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentResultGameBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.resultGameViewModel = viewModel
        binding.lifecycleOwner = this
    }

}