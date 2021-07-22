package com.example.android.recklinetest.game.start

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
import com.example.android.recklinetest.R
import com.example.android.recklinetest.databinding.FragmentStartGameBinding
import com.example.android.recklinetest.util.Animation
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class StartGameFragment : Fragment() {

    private lateinit var binding: FragmentStartGameBinding
    private val viewModel by viewModels<StartGameViewModel> {
        defaultViewModelProviderFactory
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentStartGameBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.startGameViewModel = viewModel

        lifecycleScope.launch {
            viewModel.eventsFlow
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { navigationEvent ->
                    when (navigationEvent) {
                        is StartGameViewModel.Event.NavigateToMain -> {
                            Animation.animatedButtonClick(binding.buttonStartGame)
                            findNavController()
                                .navigate(R.id.action_startGameFragment_to_mainGameFragment)
                        }
                    }

                }
        }

        viewModel.updateRecord()

    }

}