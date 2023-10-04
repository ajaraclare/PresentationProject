package com.lavaira.checklistapp.utils

import android.content.ContentValues
import androidx.annotation.IntDef
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import com.lavaira.checklistapp.R
import com.lavaira.checklistapp.utils.FragmentUtils.FragmentAnimation.Companion.TRANSITION_FADE_IN_OUT
import com.lavaira.checklistapp.utils.FragmentUtils.FragmentAnimation.Companion.TRANSITION_NONE
import com.lavaira.checklistapp.utils.FragmentUtils.FragmentAnimation.Companion.TRANSITION_POP
import com.lavaira.checklistapp.utils.FragmentUtils.FragmentAnimation.Companion.TRANSITION_SLIDE_LEFT_RIGHT
import com.lavaira.checklistapp.utils.FragmentUtils.FragmentAnimation.Companion.TRANSITION_SLIDE_LEFT_RIGHT_WITHOUT_EXIT
import timber.log.Timber
import java.lang.IndexOutOfBoundsException
import java.lang.NullPointerException

/****
 * Utility class for doing all fragment related transactions
 * Author: Lajesh Dineshkumar
 * Created on: 15/03/20
 * Modified on: 15/03/20
 *****/
object FragmentUtils {

    @IntDef(
        FragmentAnimation.TRANSITION_POP, FragmentAnimation.TRANSITION_SLIDE_LEFT_RIGHT,
        FragmentAnimation.TRANSITION_FADE_IN_OUT, FragmentAnimation.TRANSITION_SLIDE_LEFT_RIGHT_WITHOUT_EXIT,
        FragmentAnimation.TRANSITION_NONE)
    annotation class FragmentAnimation {
        companion object {
            const val TRANSITION_POP = 0
            const val TRANSITION_SLIDE_LEFT_RIGHT = 1
            const val TRANSITION_FADE_IN_OUT = 2
            const val TRANSITION_SLIDE_LEFT_RIGHT_WITHOUT_EXIT = 3
            const val TRANSITION_NONE = 4;
        }
    }



    /**
     * The method for adding a new fragment
     * @param activity : Parent Activity
     * @param frag: Fragment to be added
     * @param id: Fragment container ID
     * @param addtoBackStack: Flag indicating whether to add to backstack or not
     */
    fun addFragment(activity: AppCompatActivity, frag: androidx.fragment.app.Fragment, id: Int, addtoBackStack: Boolean) {
        val fragmentManager = activity.supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.anim_enter, R.anim.anim_exit, R.anim.anim_pop_enter, R.anim.anim_pop_exit)

        if (addtoBackStack)
            transaction.addToBackStack(frag.javaClass.canonicalName)
        transaction.add(id, frag, frag.javaClass.canonicalName)
        transaction.commit()
    }

    /**
     * The method for replacing a fragment
     * @param fragment: Fragment to be added
     * @param id: Fragment container ID
     * @param addToBackStack: Flag indicating whether to add to backstack or not
     * @param animationType: Fragment transition animation type
     */
    fun replaceFragment(fragmentManager: androidx.fragment.app.FragmentManager, fragment: androidx.fragment.app.Fragment, id: Int, addToBackStack: Boolean, @FragmentAnimation animationType:  Int) {
        val transaction = fragmentManager.beginTransaction()

        when (animationType) {
            TRANSITION_POP -> transaction.setCustomAnimations(R.anim.anim_enter, R.anim.anim_exit, R.anim.anim_pop_enter, R.anim.anim_pop_exit)
            TRANSITION_FADE_IN_OUT -> transaction.setCustomAnimations(R.anim.anim_frag_fade_in, R.anim.anim_frag_fade_out)
            TRANSITION_SLIDE_LEFT_RIGHT -> transaction.setCustomAnimations(R.anim.slide_in_from_rigth, R.anim.slide_out_to_left,
                R.anim.slide_in_from_left, R.anim.slide_out_to_right)
            TRANSITION_SLIDE_LEFT_RIGHT_WITHOUT_EXIT -> transaction.setCustomAnimations(R.anim.slide_in_from_rigth, 0)
            TRANSITION_NONE -> transaction.setCustomAnimations(0,0)
        }

        if (addToBackStack)
            transaction.addToBackStack(fragment.javaClass.canonicalName)

        transaction.replace(id, fragment, fragment.javaClass.canonicalName)
        transaction.commit()
    }


    /**
     * The method for replacing a fragment
     * @param activity : Parent Activity
     * @param fragment: Fragment to be added
     * @param id: Fragment container ID
     * @param addToBackStack: Flag indicating whether to add to backstack or not
     * @param animationType: Fragment transition animation type
     */
    fun replaceFragment(activity: AppCompatActivity, fragment: androidx.fragment.app.Fragment, id: Int, addToBackStack: Boolean, @FragmentAnimation animationType: Int) {
        val fragmentManager = activity.supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        when (animationType) {
            FragmentAnimation.TRANSITION_POP -> transaction.setCustomAnimations(R.anim.anim_enter, R.anim.anim_exit, R.anim.anim_pop_enter, R.anim.anim_pop_exit)
            FragmentAnimation.TRANSITION_FADE_IN_OUT -> transaction.setCustomAnimations(R.anim.anim_frag_fade_in, R.anim.anim_frag_fade_out)
            FragmentAnimation.TRANSITION_SLIDE_LEFT_RIGHT -> transaction.setCustomAnimations(R.anim.slide_in_from_rigth, R.anim.slide_out_to_left,
                R.anim.slide_in_from_left, R.anim.slide_out_to_right)
            FragmentAnimation.TRANSITION_SLIDE_LEFT_RIGHT_WITHOUT_EXIT -> transaction.setCustomAnimations(R.anim.slide_in_from_rigth, 0)
            FragmentAnimation.TRANSITION_NONE -> transaction.setCustomAnimations(0,0)

        }

        if (addToBackStack)
            transaction.addToBackStack(fragment.javaClass.canonicalName)

        transaction.replace(id, fragment, fragment.javaClass.canonicalName)
        transaction.commit()
    }

    /**
     * The method for replacing a fragment
     * @param activity : Parent Activity
     * @param fragment: Fragment to be added
     */
    fun openDialogFragment(activity: AppCompatActivity, fragment: androidx.fragment.app.DialogFragment, cancelable: Boolean = true) {
        fragment.isCancelable = cancelable
        fragment.show(activity.supportFragmentManager, ContentValues.TAG)
    }



    /**
     * The method for replacing a fragment without any transition animatiom
     * @param activity : Parent Activity
     * @param fragment: Fragment to be added
     * @param id: Fragment container ID
     * @param addToBackStack: Flag indicating whether to add to backstack or not
     */
    fun replaceFragmentWithoutAnimation(activity: AppCompatActivity, fragment: androidx.fragment.app.Fragment, id: Int, addToBackStack: Boolean) {
        val fragmentManager = activity.supportFragmentManager
        val fragTransaction = fragmentManager.beginTransaction()
        if (addToBackStack)
            fragTransaction.addToBackStack(fragment.javaClass.canonicalName)

        fragTransaction.replace(id, fragment, fragment.javaClass.canonicalName)
        fragTransaction.commit()
    }

    /**
     * The method for replacing a child fragment without any transition animatiom
     * @param currentFragment : Current Fragment
     * @param fragment: Fragment to be added
     * @param id: Fragment container ID
     * @param addToBackStack: Flag indicating whether to add to backstack or not
     */
    fun replaceChildFragment(currentFragment: androidx.fragment.app.Fragment, fragment: androidx.fragment.app.Fragment, id: Int, addToBackStack: Boolean) {
        val fragmentManager = currentFragment.childFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.anim_enter, R.anim.anim_exit, R.anim.pop_enter, R.anim.pop_exit)

        if (addToBackStack)
            transaction.addToBackStack(fragment.javaClass.canonicalName)

        transaction.replace(id, fragment, fragment.javaClass.canonicalName)

        transaction.commit()
    }

    fun replaceChildFragment(
        activity: AppCompatActivity?,
        currentFragment: androidx.fragment.app.Fragment,
        fragment: androidx.fragment.app.Fragment,
        id: Int,
        addToBackStack: Boolean
    ) {
        if (null == activity)
            return

        val fragmentManager = currentFragment.childFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.setCustomAnimations(
            R.anim.anim_slide_in_up,
            R.anim.anim_slide_out_up,
            R.anim.anim_slide_in_up,
            R.anim.anim_slide_out_up
        )

        if (addToBackStack)
            transaction.addToBackStack(fragment.javaClass.canonicalName)

        transaction.replace(id, fragment, fragment.javaClass.canonicalName)

        transaction.commit()
    }


    /**
     * This method pops the back the stack till the provided index fragment
     * @param activity: Parent Activity
     * @param currentFragment: current fragment
     */
    fun popToChildFragment(activity: AppCompatActivity?, currentFragment: androidx.fragment.app.Fragment, index: Int) {
        if (null == activity)
            return
        try {
            currentFragment.childFragmentManager.popBackStackImmediate(
                currentFragment.childFragmentManager.getBackStackEntryAt(index).id,
                androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
        } catch (e: IndexOutOfBoundsException) {
            Timber.log(Log.ASSERT, e.message)
        } catch (e: NullPointerException) {
            Timber.log(Log.ASSERT, e.message)
        }

    }

    /**
     * This method pops the back the stack till the first fragment
     * @param activity: Parent Activity
     */
    fun popToFirstFragment(activity: AppCompatActivity) {
        try {
            activity.supportFragmentManager.popBackStackImmediate(activity.supportFragmentManager.getBackStackEntryAt(0).id, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
        } catch (e: IndexOutOfBoundsException) {
            Timber.log(Log.ASSERT, e.message)
        } catch (e: NullPointerException) {
            Timber.log(Log.ASSERT, e.message)
        }

    }

    /**
     * This method checks whether the specified fragment is the top fragment or not
     * @param activity: Parent Activity
     * @param fragmentTag : Fragment Tag Name
     */
    fun isCurrentTopFragment(activity: AppCompatActivity, fragmentTag: String): Boolean {
        val fragmentManager = activity.supportFragmentManager
        val tag = fragmentManager.getBackStackEntryAt(fragmentManager.backStackEntryCount - 1).name
        return fragmentTag.equals(tag, ignoreCase = true)
    }

    /**
     * This method pops the backstack till the specified fragment
     * @param activity : Parent activity
     * @param tagname: Fragment Tag Name
     */
    fun popToProvidedFragment(activity: AppCompatActivity, tagname: String) {
        try {
            activity.supportFragmentManager.popBackStackImmediate(tagname, 0)
        } catch (e: IndexOutOfBoundsException) {
            Timber.log(Log.ASSERT, e.message)
        } catch (e: NullPointerException) {
            Timber.log(Log.ASSERT, e.message)
        }

    }

    /**
     * This method clears the whole backstack including the current fragment
     * @param activity: Parent activity
     */
    fun clearBackStackInclusive(activity: AppCompatActivity) {
        if (activity.supportFragmentManager.backStackEntryCount == 0)
            return
        val entry = activity.supportFragmentManager.getBackStackEntryAt(
            0)
        activity.supportFragmentManager.popBackStack(entry.id,
            androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
        activity.supportFragmentManager.executePendingTransactions()

    }

    /**
     * This method clears the backstack
     */
    fun clearBackStack(context: AppCompatActivity) {
        while (context.supportFragmentManager.backStackEntryCount != 0) {
            context.supportFragmentManager.popBackStackImmediate()
        }
    }

    /**
     * This method pops the immediate fragment
     */
    fun popBackStack(activity: AppCompatActivity) {
        activity.supportFragmentManager.popBackStack()
    }

    /**
     * This method pops the immediate fragment
     */
    fun popBackStackImmediate(activity: AppCompatActivity) {
        activity.supportFragmentManager.popBackStackImmediate()
    }

}