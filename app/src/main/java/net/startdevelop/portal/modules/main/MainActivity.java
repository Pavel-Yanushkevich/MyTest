package net.startdevelop.portal.modules.main;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import net.startdevelop.portal.R;
import net.startdevelop.portal.models.NavigationModel;
import net.startdevelop.portal.models.NewsModel;
import net.startdevelop.portal.modules.main_list.ListFragment;
import net.startdevelop.portal.modules.main_list.ListFragmentListener;
import net.startdevelop.portal.modules.navigation.NavigationFragment;
import net.startdevelop.portal.modules.navigation.NavigationFragmentListener;
import net.startdevelop.portal.modules.somenew.SomeNewActivity;

public class MainActivity extends AppCompatActivity implements ListFragmentListener, NavigationFragmentListener {

    private Toolbar toolbar;
    private String[] categories;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categories = getResources().getStringArray(R.array.categories);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        openNewsListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.navigation_frame, new NavigationFragment()).commit();
    }

    private void setToolbar() {
        toolbar.setNavigationIcon(R.mipmap.ic_list_black_36dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }else{
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
    }

    //НОВОСТИ
    private void openNewsListFragment() {
        setToolbar();
        getSupportActionBar().setTitle(categories[0]);
        Bundle bundle = new Bundle();
        bundle.putString("address", "&");
        Fragment fragmentReplace = new ListFragment();
        fragmentReplace.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragmentReplace).commit();
    }

    @Override
    public void openSomeNewFragment(NewsModel newsModel) {
        Intent intent = new Intent(this, SomeNewActivity.class);
        intent.putExtra("toolbar_name", getSupportActionBar().getTitle());
        intent.putExtra("name", newsModel.getName());
        intent.putExtra("url", newsModel.getUrl());
        intent.putExtra("time", newsModel.getTime());
        intent.putExtra("newsId", newsModel.getNewsId());
        startActivity(intent);
    }

    @Override
    public void openSomeListFragment(final NavigationModel navigationModel) {
        drawerLayout.closeDrawer(GravityCompat.START);
        setToolbar();
        Bundle bundle = new Bundle();
        if (navigationModel.getTvNavigation().equals(categories[0])){           //НОВОСТИ
            getSupportActionBar().setTitle(categories[0]);
            bundle.putString("address", "&");
        } else if (navigationModel.getTvNavigation().equals(categories[1])){    //ДЕМОТИВАТОРЫ
            getSupportActionBar().setTitle(categories[1]);
            bundle.putString("address", "&do=cat&category=demotivatory");
        } else if (navigationModel.getTvNavigation().equals(categories[2])){    //КАРТИНКИ
            getSupportActionBar().setTitle(categories[2]);
            bundle.putString("address", "&do=cat&category=kartinki");
        } else if (navigationModel.getTvNavigation().equals(categories[3])){      //ВИДЕО
            getSupportActionBar().setTitle(categories[3]);
            bundle.putString("address", "&do=cat&category=video");
        } else if (navigationModel.getTvNavigation().equals(categories[4])){      //ЮМОР
            getSupportActionBar().setTitle(categories[4]);
            bundle.putString("address", "&do=cat&category=humor");
        } else if (navigationModel.getTvNavigation().equals(categories[5])){      //APPLE
            getSupportActionBar().setTitle(categories[5]);
            bundle.putString("address", "&do=cat&category=appleipod");
        } else if (navigationModel.getTvNavigation().equals(categories[6])){      //ИНТЕРНЕТ
            getSupportActionBar().setTitle(categories[6]);
            bundle.putString("address", "&do=cat&category=inet");
        } else if (navigationModel.getTvNavigation().equals(categories[7])){      //КОМПЬЮТЕРЫ
            getSupportActionBar().setTitle(categories[7]);
            bundle.putString("address", "&do=cat&category=comp_news");
        } else if (navigationModel.getTvNavigation().equals(categories[8])){      //ГАДЖЕТЫ
            getSupportActionBar().setTitle(categories[8]);
            bundle.putString("address", "&do=cat&category=gadgets");
        } else if (navigationModel.getTvNavigation().equals(categories[9])){      //МОБИЛЬНЫЕ ТЕЛЕФОНЫ
            getSupportActionBar().setTitle(categories[9]);
            bundle.putString("address", "&do=cat&category=mobiles");
        } else if (navigationModel.getTvNavigation().equals(categories[10])){      //СЕКС
            getSupportActionBar().setTitle(categories[10]);
            bundle.putString("address", "&do=cat&category=seks-i-otnosheniya");
        } else if (navigationModel.getTvNavigation().equals(categories[11])){      //ДЕВУШКИ
            getSupportActionBar().setTitle(categories[11]);
            bundle.putString("address", "&do=cat&category=girls");
        } else if (navigationModel.getTvNavigation().equals(categories[12])){      //ИНТЕРЕСНОЕ
            getSupportActionBar().setTitle(categories[12]);
            bundle.putString("address", "&do=cat&category=poznavatelno");
        } else if (navigationModel.getTvNavigation().equals(categories[13])){      //МИРОВЫЕ НОВОСТИ
            getSupportActionBar().setTitle(categories[13]);
            bundle.putString("address", "&do=cat&category=mirovye_novosti");
        } else if (navigationModel.getTvNavigation().equals(categories[14])){      //НОВОСТИ БЕЛАРУСИ
            getSupportActionBar().setTitle(categories[14]);
            bundle.putString("address", "&do=cat&category=novosti_belarusi");
        } else if (navigationModel.getTvNavigation().equals(categories[15])){      //ПРОИСШЕСТВИЯ
            getSupportActionBar().setTitle(categories[15]);
            bundle.putString("address", "&do=cat&category=proishestviya");
        } else if (navigationModel.getTvNavigation().equals(categories[16])){      //СПОРТ
            getSupportActionBar().setTitle(categories[16]);
            bundle.putString("address", "&do=cat&category=sport");
        } else if (navigationModel.getTvNavigation().equals(categories[17])){      //КИНО И ШОУБИЗНЕС
            getSupportActionBar().setTitle(categories[17]);
            bundle.putString("address", "&do=cat&category=novosti_kino_i_shoubiznes");
        } else if (navigationModel.getTvNavigation().equals(categories[18])){      //АВТО
            getSupportActionBar().setTitle(categories[18]);
            bundle.putString("address", "&do=cat&category=auto");
        } else if (navigationModel.getTvNavigation().equals(categories[19])){      //КРАСОТА И ЗДОРОВЬЕ
            getSupportActionBar().setTitle(categories[19]);
            bundle.putString("address", "&do=cat&category=zdorove-i-krasota");
        } else if (navigationModel.getTvNavigation().equals(categories[20])){      //КРЕАТИВ
            getSupportActionBar().setTitle(categories[20]);
            bundle.putString("address", "&do=cat&category=creo");
        }
        Fragment fragmentReplace = new ListFragment();
        fragmentReplace.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragmentReplace).commit();
    }
}
