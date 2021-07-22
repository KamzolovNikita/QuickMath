package com.example.android.recklinetest.game.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.android.recklinetest.databinding.FragmentMainGameBinding
import com.example.android.recklinetest.util.Animation
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class MainGameFragment : Fragment() {

    private lateinit var binding: FragmentMainGameBinding
    private val viewModel by viewModels<MainGameViewModel> {
        defaultViewModelProviderFactory
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMainGameBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.mainGameViewModel = viewModel

        lifecycleScope.launch {
            viewModel.eventsFlow
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { event ->
                    when (event) {
                        is MainGameViewModel.Event.NavigateToResult -> {
                            val action =
                                MainGameFragmentDirections.actionMainGameFragmentToResultGameFragment(
                                    viewModel.rightAnswers,
                                    viewModel.totalQuestions
                                )
                            findNavController()
                                .navigate(action)
                        }
                        is MainGameViewModel.Event.CorrectAnswer -> {
                            Animation.animatedAnswerHighlighting(binding.viewCorrectAnswer)
                        }

                        is MainGameViewModel.Event.IncorrectAnswer -> {
                            Animation.animatedAnswerHighlighting(binding.viewIncorrectAnswer)
                        }
                    }

                }
        }

        setKeyboardBindings()
    }

    private fun setKeyboardBindings() {
        binding.buttonKeyboardOne.setOnClickListener {
            Animation.animatedButtonClick(it)
            viewModel.keyboardPressedNumber(1)
        }
        binding.buttonKeyboardTwo.setOnClickListener {
            Animation.animatedButtonClick(it)
            viewModel.keyboardPressedNumber(2)
        }
        binding.buttonKeyboardThree.setOnClickListener {
            Animation.animatedButtonClick(it)
            viewModel.keyboardPressedNumber(3)
        }
        binding.buttonKeyboardFour.setOnClickListener {
            Animation.animatedButtonClick(it)
            viewModel.keyboardPressedNumber(4)
        }
        binding.buttonKeyboardFive.setOnClickListener {
            Animation.animatedButtonClick(it)
            viewModel.keyboardPressedNumber(5)
        }
        binding.buttonKeyboardSix.setOnClickListener {
            Animation.animatedButtonClick(it)
            viewModel.keyboardPressedNumber(6)
        }
        binding.buttonKeyboardSeven.setOnClickListener {
            Animation.animatedButtonClick(it)
            viewModel.keyboardPressedNumber(7)
        }
        binding.buttonKeyboardEight.setOnClickListener {
            Animation.animatedButtonClick(it)
            viewModel.keyboardPressedNumber(8)
        }
        binding.buttonKeyboardNine.setOnClickListener {
            Animation.animatedButtonClick(it)
            viewModel.keyboardPressedNumber(9)
        }
        binding.buttonKeyboardZero.setOnClickListener {
            Animation.animatedButtonClick(it)
            viewModel.keyboardPressedNumber(0)
        }
        binding.buttonKeyboardBackspace.setOnClickListener {
            Animation.animatedButtonClick(it)
            viewModel.keyboardPressedBackspace()
        }
        binding.buttonKeyboardCheckMark.setOnClickListener {
            Animation.animatedButtonClick(it)
            viewModel.keyboardPressedCheckMark()
        }
    }

}