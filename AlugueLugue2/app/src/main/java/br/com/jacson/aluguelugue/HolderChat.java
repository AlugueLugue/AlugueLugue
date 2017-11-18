package alugueluguebrasilnovo.alugueluguebrasil.objetosUsados;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import alugueluguebrasilnovo.alugueluguebrasil.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by NAILSON on 11/11/2017.
 */

public class HolderChat extends RecyclerView.ViewHolder {

    private TextView nome;
    private CircleImageView imagem;

    public HolderChat(View itemView) {
        super(itemView);
        nome = (TextView) itemView.findViewById(R.id.displayNameUser);
        imagem = (CircleImageView) itemView.findViewById(R.id.imagem_chats);
    }

    public TextView getNome() {
        return nome;
    }

    public void setNome(TextView nome) {
        this.nome = nome;
    }

    public CircleImageView getImagem() {
        return imagem;
    }

    public void setImagem(CircleImageView imagem) {
        this.imagem = imagem;
    }
}
