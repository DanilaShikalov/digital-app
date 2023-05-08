package com.example.digitalproject.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.digitalproject.R;
import com.example.digitalproject.models.DocumentModel;

import java.util.ArrayList;
import java.util.List;

public class DocumentAdapter extends BaseAdapter {
    private List<DocumentModel> documents = new ArrayList<>();
    private Context context;

    public DocumentAdapter(Context context) {
        this.context = context;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        LayoutInflater documentInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        DocumentModel document = documents.get(i);

        convertView = documentInflater.inflate(R.layout.document, null);
        ((TextView) convertView.findViewById(R.id.document_type)).setText(document.getType());
        ((TextView) convertView.findViewById(R.id.email)).setText(document.getEmail());
        ((TextView) convertView.findViewById(R.id.size_document)).setText(document.getSize());
        return convertView;
    }

    public void add(DocumentModel documentModel) {
        this.documents.add(documentModel);
        notifyDataSetChanged();
    }

    public void clear() {
        documents.clear();
    }

    @Override
    public int getCount() {
        return documents.size();
    }

    @Override
    public Object getItem(int i) {
        return documents.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
}
