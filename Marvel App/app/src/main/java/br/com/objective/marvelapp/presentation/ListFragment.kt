package br.com.objective.marvelapp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import br.com.objective.marvelapp.R
import br.com.objective.marvelapp.domain.entities.Hero
import br.com.objective.marvelapp.domain.entities.Heroes
import br.com.objective.marvelapp.util.Data
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.list_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment : Fragment(R.layout.list_fragment), OnListPaginationInteractionListener {

    private val viewModel: ListViewModel by viewModel()
    private val hero1View: View get() = hero1
    private val hero2View: View get() = hero2
    private val hero3View: View get() = hero3
    private val hero4View: View get() = hero4
    private val line1View: View get() = line1
    private val line2View: View get() = line2
    private val line3View: View get() = line3
    private val line4View: View get() = line4
    private val pagination: View get() = pages
    private val search: TextInputLayout get() = outlinedTextField
    private lateinit var hero1Controller: HeroViewController
    private lateinit var hero2Controller: HeroViewController
    private lateinit var hero3Controller: HeroViewController
    private lateinit var hero4Controller: HeroViewController
    private lateinit var paginationViewController: PaginationViewController
    private var observer: Observer<Data<Heroes>>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hero1Controller = HeroViewController(hero1View)
        hero2Controller = HeroViewController(hero2View)
        hero3Controller = HeroViewController(hero3View)
        hero4Controller = HeroViewController(hero4View)
        paginationViewController = PaginationViewController(pagination, requireContext(), this)
        handleList(Heroes(0, 0, 0, 0, emptyList()))
        search.editText?.doOnTextChanged { inputText, _, _, _ ->
            viewModel.resetMode()
            if (inputText.isNullOrEmpty()) {
                call(false)
            } else {
                call(false, inputText.toString())
            }
        }
        call(false)
    }

    // TODO: Move to a ViewController
    private fun handleList(heroes: Heroes) {
        paginationViewController.setAll(heroes.offset, heroes.total, heroes.limit)
        var len = heroes.results.size
        if (len >= 1) {
            setHero(hero1Controller, heroes.results[0])
            line1View.visibility = VISIBLE
        } else {
            hero1Controller.changeVisibility(false)
            line1View.visibility = INVISIBLE
        }
        if (len >= 2) {
            setHero(hero2Controller, heroes.results[1])
            line2View.visibility = VISIBLE
        } else {
            hero2Controller.changeVisibility(false)
            line2View.visibility = INVISIBLE
        }
        if (len >= 3) {
            setHero(hero3Controller, heroes.results[2])
            line3View.visibility = VISIBLE
        } else {
            hero3Controller.changeVisibility(false)
            line3View.visibility = INVISIBLE
        }
        if (len >= 4) {
            setHero(hero4Controller, heroes.results[3])
            line4View.visibility = VISIBLE
        } else {
            hero4Controller.changeVisibility(false)
            line4View.visibility = INVISIBLE
        }
    }

    private fun setHero(heroViewController: HeroViewController, hero: Hero) {
        heroViewController.changeVisibility(true)
        heroViewController.setTitle(hero.name)
        heroViewController.setImage(hero.thumbnail.path, hero.thumbnail.extension)
    }

    private fun call(previous: Boolean) {
        if (observer != null)
            viewLifecycleOwnerLiveData.removeObservers(viewLifecycleOwner)
        observer = Observer {
            if (it.isSuccessful() && it.data != null) {
                handleList(it.data!!)
            } else {
                // TODO: Treat UI Exception
            }
        }
        if (previous) {
            viewModel.getHeroesPrevious().observe(viewLifecycleOwner, observer!!)
        } else {
            viewModel.getHeroes().observe(viewLifecycleOwner, observer!!)
        }
    }

    private fun call(previous: Boolean, query: String) {
        if (observer != null)
            viewLifecycleOwnerLiveData.removeObservers(viewLifecycleOwner)
        observer = Observer {
            if (it.isSuccessful() && it.data != null) {
                handleList(it.data!!)
            } else {
                // TODO: Treat UI Exception
            }
        }
        if (previous) {
            viewModel.getHeroesPrevious(query).observe(viewLifecycleOwner, observer!!)
        } else {
            viewModel.getHeroes(query).observe(viewLifecycleOwner, observer!!)
        }
    }

    override fun onPreviousInteraction() {
        val inputText = search.editText?.text.toString()
        if (inputText.isNullOrEmpty()) {
            call(true)
        } else {
            call(true, inputText)
        }
    }

    override fun onNextInteraction() {
        val inputText = search.editText?.text.toString()
        if (inputText.isNullOrEmpty()) {
            call(false)
        } else {
            call(false, inputText)
        }
    }
}

interface OnListPaginationInteractionListener {
    fun onPreviousInteraction()
    fun onNextInteraction()
}