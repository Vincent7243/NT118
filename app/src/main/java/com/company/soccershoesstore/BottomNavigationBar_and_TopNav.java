package com.company.soccershoesstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class BottomNavigationBar_and_TopNav extends AppCompatActivity {
    private Toolbar toolbar;
    private BottomNavigationView mNavigationView;
    private ViewPager mViewPager;

    private int getUnfilledIcon(int itemId) {
        if (itemId == R.id.action_cozy) {
            return R.drawable.ic_cozy;
        } else if (itemId == R.id.action_love) {
            return R.drawable.ic_love;
        } else if (itemId == R.id.action_home) {
            return R.drawable.ic_home;
        } else if (itemId == R.id.action_notification) {
            return R.drawable.ic_notification;
        } else if (itemId == R.id.action_person) {
            return R.drawable.ic_person;
        } else {
            return -1;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_and_top_navigation_bar);
        mNavigationView = findViewById(R.id.bottom_navigation_bar);
        mViewPager = findViewById(R.id.view_pager);
        toolbar = findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);
        setTitle("");

        setUpViewPager();
        mNavigationView.setSelectedItemId(R.id.action_home);
        mNavigationView.getMenu().getItem(2).setIcon(R.drawable.ic_home_fill);
        mViewPager.setCurrentItem(2);

        mNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                // Gỡ bỏ fill của tất cả các tấm hình trước khi đặt fill cho tấm hình mới được chọn
                Menu menu = mNavigationView.getMenu();
                for (int i = 0; i < menu.size(); i++) {
                    MenuItem menuItem = menu.getItem(i);
                    if (menuItem.getItemId() != item.getItemId()) {
                        // Gỡ bỏ fill của các tấm hình khác
                        menuItem.setIcon(getUnfilledIcon(menuItem.getItemId()));
                    }
                }

                // Đặt fill cho tấm hình được chọn
                int itemId = item.getItemId();
                if (itemId == R.id.action_cozy) {
                    item.setIcon(R.drawable.ic_cozy_fill);
                    mViewPager.setCurrentItem(0);
                } else if (itemId == R.id.action_love) {
                    item.setIcon(R.drawable.ic_love_fill);
                    mViewPager.setCurrentItem(1);
                } else if (itemId == R.id.action_home) {
                    item.setIcon(R.drawable.ic_home_fill);
                    mViewPager.setCurrentItem(2);
                } else if (itemId == R.id.action_notification) {
                    item.setIcon(R.drawable.ic_notification_fill);
                    mViewPager.setCurrentItem(3);
                } else if (itemId == R.id.action_person) {
                    item.setIcon(R.drawable.ic_person_fill);
                    mViewPager.setCurrentItem(4);
                }
                return true;
            }
            private int getUnfilledIcon(int itemId) {
                if (itemId == R.id.action_cozy) {
                    return R.drawable.ic_cozy;
                } else if (itemId == R.id.action_love) {
                    return R.drawable.ic_love;
                } else if (itemId == R.id.action_home) {
                    return R.drawable.ic_home;
                } else if (itemId == R.id.action_notification) {
                    return R.drawable.ic_notification;
                } else if (itemId == R.id.action_person) {
                    return R.drawable.ic_person;
                } else {
                    return -1;
                }
            }

        });
    }
    private void toggleSearchMenuItem(boolean isVisible) {
        MenuItem searchItem = toolbar.getMenu().findItem(R.id.action_search);
        if (searchItem != null) {
            searchItem.setVisible(isVisible);
        }
    }


    private void setUpViewPager(){
        ViewPagerAdapter_BottomNavBar viewPagerAdapterBottomNavBar=new ViewPagerAdapter_BottomNavBar(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(viewPagerAdapterBottomNavBar);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Menu menu = mNavigationView.getMenu();
                for (int i = 0; i < menu.size(); i++) {
                    MenuItem menuItem = menu.getItem(i);
                    menuItem.setIcon(getUnfilledIcon(menuItem.getItemId()));
                }
                MenuItem selectedItem = menu.getItem(position);
                selectedItem.setIcon(getFilledIcon(selectedItem.getItemId()));

                for (int i = 0; i < menu.size(); i++) {
                    MenuItem menuItem = menu.getItem(i);
                    menuItem.setChecked(false);
                }
                selectedItem.setChecked(true);

                // Kiểm tra nếu fragment hiện tại là Home
                if (position == 2) { // 2 là vị trí của Home fragment
                    toggleSearchMenuItem(true);
                } else {
                    toggleSearchMenuItem(false);
                }
            }

            private int getFilledIcon(int itemId) {
                if (itemId == R.id.action_cozy) {
                    return R.drawable.ic_cozy_fill;
                } else if (itemId == R.id.action_love) {
                    return R.drawable.ic_love_fill;
                } else if (itemId == R.id.action_home) {
                    return R.drawable.ic_home_fill;
                } else if (itemId == R.id.action_notification) {
                    return R.drawable.ic_notification_fill;
                } else if (itemId == R.id.action_person) {
                    return R.drawable.ic_person_fill;
                } else {
                    return -1;
                }
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_top_navigation, menu);

        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(@NonNull MenuItem item) {
                Toast.makeText(BottomNavigationBar_and_TopNav.this, "Search is Expanded", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(@NonNull MenuItem item) {
                Toast.makeText(BottomNavigationBar_and_TopNav.this, "Search is Collapse", Toast.LENGTH_SHORT).show();
                return true;
            }
        };
        menu.findItem(R.id.action_search).setOnActionExpandListener(onActionExpandListener);
        SearchView searchView = (SearchView)menu.findItem(R.id.action_search).getActionView();
        assert searchView != null;
        searchView.setQueryHint("Search Data here...");

        // Set up search listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle search query submit
                performSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle search query text change
                performSearch(newText);
                return false;
            }
        });

        // Ẩn nút tìm kiếm nếu không phải ở Home fragment
        toggleSearchMenuItem(mViewPager.getCurrentItem() == 2);

        return true;
    }
    private void performSearch(String query) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.all_fragment_category);

        if (currentFragment instanceof AllCategoryFragment) {
            ((AllCategoryFragment) currentFragment).searchProducts(query);
        } else {
            Log.d("SEARCH", "Current fragment is not AllCategoryFragment");
        }
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_chat) {
            Intent chatIntent = new Intent(this, ChatActivity.class);
            startActivity(chatIntent);
            return true;
        } else if (id == R.id.action_cart) {
            Intent cartIntent = new Intent(this, CartActivity.class);
            startActivity(cartIntent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}