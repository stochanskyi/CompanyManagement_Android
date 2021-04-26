package today.magnolia.android.screens.main.bottomnavigation

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mars.companymanagement.presentation.screens.main.bottomnavigation.items.NavigationItem
import java.util.*

class BottomNavigationController(
    private val fragmentManager: FragmentManager,
    private val containerId: Int
) {

    private var items: List<NavigationItem> = emptyList()

    private val tabsBackStack: Stack<NavigationItem> = Stack()

    private var firstDefaultItem: NavigationItem? = null

    private var currentItem: NavigationItem? = null

    private var navigationView: BottomNavigationView? = null

    fun setItems(items: List<NavigationItem>) {
        this.items = items
        firstDefaultItem = items.first()
        setupHosts(containerId)
    }

    fun setup(view: BottomNavigationView) {
        navigationView = view

        view.setOnNavigationItemSelectedListener {
            onItemSelected(it.itemId)
        }

        view.selectedItemId = firstDefaultItem?.menuId ?: return
    }

    fun canHandleBackPressInternal(): Boolean {
        val controller = currentItem?.getHost()?.navController ?: return false

        return controller.currentDestination?.id != controller.graph.startDestination
    }

    fun handleBackPressed(): Boolean {
        tabsBackStack.pop()
        if (tabsBackStack.isEmpty()) return false

        val nextItem = tabsBackStack.pop()
        navigationView?.selectedItemId = nextItem.menuId

        return true
    }

    fun openTab(tabTag: String) {
        val tab = items.firstOrNull { it.tag == tabTag } ?: return
        navigationView?.selectedItemId = tab.menuId
    }

    private fun setupHosts(containerId: Int) {
        val transaction = fragmentManager.beginTransaction()
        items.forEach { item ->
            transaction.setupItem(containerId, item)
        }
        transaction.commitNow()
    }

    private fun onItemSelected(selectedItemId: Int): Boolean {
        val selectedItem = items.firstOrNull { it.menuId == selectedItemId } ?: return false

        selectedItem.addToTabsBackStack()

        fragmentManager.beginTransaction()
            .changeTab(selectedItem)
            .commit()

        return true
    }

    private fun FragmentTransaction.setupItem(containerId: Int, item: NavigationItem) {
        if (fragmentManager.findFragmentByTag(item.tag) != null) return

        val hostFragment = createHost(item)
        add(containerId, hostFragment, item.tag)
        detach(hostFragment)
    }

    private fun FragmentTransaction.changeTab(item: NavigationItem): FragmentTransaction {
        currentItem?.getHost()?.let { detach(it) }

        item.getHost()?.let {
            attach(it)
            setPrimaryNavigationFragment(it)
        }
        currentItem = item

        return this
    }

    private fun NavigationItem.getHost(): NavHostFragment? {
        return fragmentManager.findFragmentByTag(tag) as? NavHostFragment
    }

    private fun createHost(item: NavigationItem): NavHostFragment {
        return NavHostFragment.create(item.navigationGraphId)
    }

    private fun NavigationItem.addToTabsBackStack() {
        tabsBackStack.removeAll { it.tag == this.tag }
        tabsBackStack.push(this)
    }

    fun release() {
        navigationView = null
    }
}
