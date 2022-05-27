package beyzagobel.depo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import co.dift.ui.SwipeToAction;


public class UrunlerRecyclerViewAdapter extends RecyclerView.Adapter<UrunlerRecyclerViewAdapter.UrunlerViewHolder>{

    private List<Urunler> urunler;

    public UrunlerRecyclerViewAdapter(List<Urunler> urunler){
        this.urunler= urunler;
    }

    public class UrunlerViewHolder extends SwipeToAction.ViewHolder<Urunler> {

        private TextView lblUrunAdi,lblUrunAdeti,lblBarkod,lblUrunDepoAdi;

        public UrunlerViewHolder(@NonNull View view) {
            super(view);
            lblUrunAdi = view.findViewById(R.id.lblUrunAdi);
            lblUrunAdeti = view.findViewById(R.id.lblUrunAdeti);
            lblBarkod = view.findViewById(R.id.lblBarkod);

        }
    }

    @NonNull
    @Override
    public UrunlerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_element_urunler,parent,false);
        return new UrunlerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UrunlerViewHolder holder, int position) {
        Urunler urun = urunler.get(position);

        holder.lblUrunAdi.setText("Urun Adı: "+urun.getUrunAdi());
        holder.lblUrunAdeti.setText("Urun Adeti: "+urun.getUrunAdeti());
        holder.lblBarkod.setText("Barkod: "+urun.getBarkod());
        holder.data = urun ;
    }

    @Override
    public int getItemCount() {
        return urunler.size();
    }


}
