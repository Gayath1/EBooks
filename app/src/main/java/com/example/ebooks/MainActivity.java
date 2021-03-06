package com.example.ebooks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.example.ebooks.adapters.PagerAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ViewPager2 viewPager2;
    private TabLayout tabLayout;

    private TextView drawerName;
    private TextView drawerEmail;

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private PagerAdapter pagerAdapter;
    private FirebaseAuth firebaseAuth;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private int[] titles = new int[]{R.drawable.ic_home, R.drawable.ic_category, R.drawable.ic_order, R.drawable.ic_profile};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.mainToolbar);
        drawerLayout = findViewById(R.id.mainDrawer);
        navigationView = findViewById(R.id.mainNavigationView);
        viewPager2 = findViewById(R.id.mainViewPager);
        tabLayout = findViewById(R.id.mainTabLayout);

        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();
        sharedPreferences = getApplicationContext().getSharedPreferences("pickABook", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String currentUserName = sharedPreferences.getString("loggedUserName", "");
        boolean isAdmin = sharedPreferences.getBoolean("isAdmin", false);

        navigationView.setNavigationItemSelectedListener(this);
        View navigationHeader = navigationView.getHeaderView(0);

        drawerName = navigationHeader.findViewById(R.id.drawerHeaderName);
        drawerEmail = navigationHeader.findViewById(R.id.drawerHeaderEmail);

        drawerName.setText(currentUserName);
        drawerEmail.setText(currentUser != null && currentUser.getEmail() != null ? currentUser.getEmail() : "-");

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        pagerAdapter = new PagerAdapter(this, tabLayout.getTabCount(), isAdmin);
        viewPager2.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setIcon(titles[position]);
            }
        }).attach();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.actionCart) {
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);

        if (item.getItemId() == R.id.itemCat) {
            TabLayout.Tab tab = tabLayout.getTabAt(1);
            tab.select();
        }
        if (item.getItemId() == R.id.itemHome) {
            TabLayout.Tab tab = tabLayout.getTabAt(0);
            tab.select();
        }
        if (item.getItemId() == R.id.itemOrders) {
            TabLayout.Tab tab = tabLayout.getTabAt(2);
            tab.select();
        }
        if (item.getItemId() == R.id.itemMyProfile) {
            TabLayout.Tab tab = tabLayout.getTabAt(3);
            tab.select();
        }
        if (item.getItemId() == R.id.itemSignOut) {
            firebaseAuth.signOut();
            editor.putString("loggedUserName", "");
            editor.putBoolean("isLoggedIn", false);
            editor.putBoolean("isAdmin", false);
            editor.apply();

            Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        return false;
    }
}