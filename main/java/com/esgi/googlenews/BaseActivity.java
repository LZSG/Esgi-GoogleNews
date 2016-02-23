package com.esgi.googlenews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.esgi.googlenews.Modeles.UpdateListArticlesService;

public class BaseActivity extends AppCompatActivity
{

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_manual_update:
                this.updateArticles();
                break;

            case R.id.action_settings:
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateArticles() {
        startService(new Intent(this, UpdateListArticlesService.class));
    }
}
