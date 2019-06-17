package sv.edu.ues.fia.eisi.fernanda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ControlDB extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Fernanda.db";

    public ControlDB(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE CONTACTOS(ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, NOMBRE TEXT NOT NULL, TELEFONO TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int OldVersion, int newVersion){
    }

    public void llenarBase(){
        SQLiteDatabase db = getWritableDatabase();
        String count = "SELECT count(*) FROM CONTACTOS";
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if(icount>0) {

        }else{
            db.execSQL("INSERT INTO CONTACTOS values(1, 'Carlos Herrera', '70012314')");
            db.execSQL("INSERT INTO CONTACTOS values(2, 'Daniel Hernandez', '73541293')");
            db.execSQL("INSERT INTO CONTACTOS values(3, 'Mayra Aquino', '71230475')");
            db.execSQL("INSERT INTO CONTACTOS values(4, 'Patricia Orellana', '70894657')");
            db.execSQL("INSERT INTO CONTACTOS values(5, 'Daniel Reyes', '61019234')");
            db.execSQL("INSERT INTO CONTACTOS values(6, 'Miguel Rivera', '75465867')");
            db.execSQL("INSERT INTO CONTACTOS values(7, 'Jose Adolfo', '19234859')");
            db.execSQL("INSERT INTO CONTACTOS values(8, 'Tatiana Rivera', '72456789')");
            db.execSQL("INSERT INTO CONTACTOS values(9, 'Angela Martinez', '71457689')");
            db.execSQL("INSERT INTO CONTACTOS values(10, 'Omar Orellana', '71547689')");
        }
    }

    public boolean agregarContacto(String nombre, String telefono) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("NOMBRE", nombre);
        contentvalues.put("TELEFONO", telefono);
        long result = db.insert("CONTACTOS",null, contentvalues);
        db.close();
        //To check whether Data is Inserted in DataBase
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    private static final String [] camposContacto = new String[] {"TELEFONO", "NOMBRE"};

    public String buscarContacto(String nombre) {
        SQLiteDatabase db = getWritableDatabase();
        String [] nombreid = {nombre};
        Cursor cursor = db.query("CONTACTOS", camposContacto, "NOMBRE = ?", nombreid, null, null, null);
        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        } else {
            return "";
        }
    }
}
