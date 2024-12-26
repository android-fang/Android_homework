package com.example.casper.Experiment2024;




import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SparkPosBank {
    final String DATA_FILENAME = "sparkPos.data";

    public ArrayList<SparkPos> LoadPos(Context context) {
        ArrayList<SparkPos> Pos = new ArrayList<>();
        try {
            FileInputStream fileIn = context.openFileInput(DATA_FILENAME);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Pos = (ArrayList<SparkPos>) objectIn.readObject();
            objectIn.close();
            fileIn.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Pos;
    }

    public void SavePos(Context context, ArrayList<SparkPos> pos) {
        try {
            FileOutputStream fileOut = context.openFileOutput(DATA_FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(pos);
            out.close();
            fileOut.close();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}

