package beyzagobel.depo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import co.dift.ui.SwipeToAction;

public class UrunlerActivity extends AppCompatActivity {

    private RecyclerView rycUrunler;
    FirebaseDatabase database;
    Urunler urunler;
    Intent gelenIntent;
    ArrayList<Urunler> urunlerArrayList;
    UrunlerRecyclerViewAdapter adapter;
    SwipeToAction swp;
    FloatingActionButton urunEkleFab;
    String gelenDepoId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urunler);

        database = FirebaseDatabase.getInstance();
        initComponents();
        urunleriGetir();
        urunEkleFab();
        swp();
    }

    private void swp() {
        swp = new SwipeToAction(rycUrunler, new SwipeToAction.SwipeListener<Urunler>() {
            @Override
            public boolean swipeLeft(Urunler urun) {
                // ÜRÜN GÜNCELLEME
                Intent guncelleIntent = new Intent(UrunlerActivity.this, UrunEkleActivity.class);
                guncelleIntent.putExtra("depoId", gelenDepoId);
                guncelleIntent.putExtra("urunId",urun.getUrunId());
                startActivity(guncelleIntent);
                return true;

            }
            @Override
            public boolean swipeRight(Urunler urun) {
                // ÜRÜN SİLME
                DatabaseReference dRurun = FirebaseDatabase.getInstance().getReference("urunler").child(gelenDepoId).child(urun.getUrunId());
                dRurun.removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        if (error == null) {
                            Snackbar snackbar = Snackbar.make(rycUrunler, urun.getUrunAdi() + " Silindi!",
                                    Snackbar.LENGTH_SHORT);
                            snackbar.show();

                        }
                    }
                });
                return true;
            }
            @Override
            public void onClick(Urunler urun) {
            }

            @Override
            public void onLongClick(Urunler urun) {
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.eklemenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.done:
                Intent kaydetIntent = new Intent(UrunlerActivity.this, UrunEkleActivity.class);
                kaydetIntent.putExtra("depoId", gelenDepoId);
                startActivity(kaydetIntent);
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }


    private void urunEkleFab() {
        urunEkleFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kaydetIntent = new Intent(UrunlerActivity.this, UrunEkleActivity.class);
                kaydetIntent.putExtra("depoId", gelenDepoId);
                startActivity(kaydetIntent);
            }
        });
    }

    private void urunleriGetir() {
        try {
            gelenIntent = getIntent();
            gelenDepoId = gelenIntent.getStringExtra("depoId");
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("urunler").child(gelenDepoId);
            urunlerArrayList = new ArrayList<>();
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        urunler = ds.getValue(Urunler.class);
                        String urunAdi = urunler.getUrunAdi();
                        String urunId = urunler.getUrunId();
                        long barkod = urunler.getBarkod();
                        long urunAdeti = urunler.getUrunAdeti();

                        urunlerArrayList.add(new Urunler(urunId, urunAdi, urunAdeti, barkod));
                        LinearLayoutManager llm = new LinearLayoutManager(UrunlerActivity.this);
                        rycUrunler.setLayoutManager(llm);
                        rycUrunler.setHasFixedSize(true);
                        adapter = new UrunlerRecyclerViewAdapter(urunlerArrayList);
                        rycUrunler.setAdapter(adapter);
                        rycUrunler.addItemDecoration(new DividerItemDecoration(UrunlerActivity.this, LinearLayoutManager.VERTICAL));
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {
        rycUrunler = findViewById(R.id.rycUrunler);
        urunEkleFab = findViewById(R.id.urunEkleFab);
    }
}