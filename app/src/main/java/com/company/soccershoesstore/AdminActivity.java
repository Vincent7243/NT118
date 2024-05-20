package com.company.soccershoesstore;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class AdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar=findViewById(R.id.admin_toolbar);
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.admin_drawerlayout);
        NavigationView navigationView=findViewById(R.id.drawer_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.nav_open,R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if(savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.AdminFragmentMain,new AdminProductFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_mproduct);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == R.id.nav_mproduct) {
            getSupportFragmentManager().beginTransaction().replace(R.id.AdminFragmentMain, new AdminProductFragment()).commit();
        } else if (itemId == R.id.nav_mbill) {
            getSupportFragmentManager().beginTransaction().replace(R.id.AdminFragmentMain, new AdminBillFragment()).commit();
        } else if (itemId == R.id.nav_chat) {
            getSupportFragmentManager().beginTransaction().replace(R.id.AdminFragmentMain, new AdminChatFragment()).commit();
        } else if (itemId == R.id.nav_sale) {
            getSupportFragmentManager().beginTransaction().replace(R.id.AdminFragmentMain, new AdminSaleFragment()).commit();
        } else if (itemId == R.id.nav_static) {
            getSupportFragmentManager().beginTransaction().replace(R.id.AdminFragmentMain, new AdminStaticFragment()).commit();
        } else if (itemId == R.id.nav_logout) {
            Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return  true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer((GravityCompat.START));
        }else {
            super.onBackPressed();
        }
    }
}