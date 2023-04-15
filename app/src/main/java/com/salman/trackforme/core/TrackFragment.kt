package com.salman.trackforme.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.salman.trackforme.core.navigation.NavigationCommand
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/8/2023.
 */
abstract class TrackFragment<ViewModel : TrackViewModel, Binding : ViewBinding>(
    private val inflateBinding: (LayoutInflater, ViewGroup?, Boolean) -> Binding,
) : Fragment() {

    private var _binding: Binding? = null
    protected val binding: Binding
        get() = _binding!!

    abstract val viewModel: ViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = inflateBinding(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.navigationCommands.onEach(::handleNavigationCommand).launchIn(lifecycleScope)
    }


    open fun handleNavigationCommand(navigationCommand: NavigationCommand?) {
        if (navigationCommand == null) return

        when (navigationCommand) {
            is NavigationCommand.To -> {
                findNavController().navigate(navigationCommand.destination)
            }

            is NavigationCommand.BackTo -> {
                findNavController().popBackStack(navigationCommand.destinationId, navigationCommand.inclusive)
            }

            is NavigationCommand.Back -> {
                findNavController().popBackStack()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}