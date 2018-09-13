package com.app.kargo;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.app.kargo.FragmentLayout.AccountFragment;
import com.app.kargo.FragmentLayout.AssistanceFragment;
import com.app.kargo.FragmentLayout.DeliveriesFragment;
import com.app.kargo.FragmentLayout.ParametersFragment;
import com.app.kargo.FragmentLayout.PlanningFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private Toolbar myToolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ColorStateList colorStateList;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        int[][] states = new int[][] {
                new int[] {android.R.attr.state_checked}, // checked
                new int[] {-android.R.attr.state_checked}, // unchecked
        };

        int[] colors = new int[] {
                getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.text),
        };

        colorStateList = new ColorStateList(states, colors);

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_list_white_36dp);
        setSupportActionBar(myToolbar);


        //Mise en place du menu lateral
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(colorStateList);

        MenuItem menuItem = navigationView.getMenu().getItem(0);
        menuItem.setChecked(true);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                myToolbar,
                R.string.open_closer,
                R.string.close_closer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

        };
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(Gravity.START);

            }
        });

        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        //Mise en place du Fragment des missions
        DeliveriesFragment fragment = new DeliveriesFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
        } else {
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Menu menu = navigationView.getMenu();

        for (int i = 0; i < menu.size(); i++) {

            MenuItem menuItem = menu.getItem(i);
            menuItem.setChecked(false);

        }

        item.setChecked(true);

        //Gere les selections d'elements dans le menu lateral et les transitions de Fragments/Pages
        int id = item.getItemId();

        if (id == R.id.menu_item_mission) {


            DeliveriesFragment fragment = new DeliveriesFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
            myToolbar.setTitle(R.string.title_item_mission);

        } else if (id == R.id.menu_item_account) {

            AccountFragment fragment = new AccountFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
            myToolbar.setTitle(R.string.title_item_account);

        } else if (id == R.id.menu_item_planning){

            PlanningFragment fragment = new PlanningFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
            myToolbar.setTitle(R.string.title_item_planning);

        } else if (id == R.id.menu_item_assistance){

            AssistanceFragment fragment = new AssistanceFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
            myToolbar.setTitle(R.string.title_item_assistance);

        } else if (id == R.id.menu_item_parameter){

            ParametersFragment fragment = new ParametersFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
            myToolbar.setTitle(R.string.title_item_parameters);

        }else if (id == R.id.menu_item_faq) {

        } else if (id == R.id.menu_item_logout) {
            signOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
