package monash.example.newlibrary.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class LibraryContentProvider extends ContentProvider {

    public static final String CONTENT_AUTHORITY = "fit2081.app.phat";
    public static final Uri CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    LibrariesDatabase db;

    public LibraryContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        int deletedCount=  db.getOpenHelper().getWritableDatabase().delete(Library.TABLE_NAME,selection,selectionArgs);
        return deletedCount;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
       long newId =  db.getOpenHelper().getWritableDatabase().insert(Library.TABLE_NAME,0,values);
       return ContentUris.withAppendedId(CONTENT_URI,newId);
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        db = LibrariesDatabase.getLibraryDB(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        //openHelper use to have access to databasee without using ROM database

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(Library.TABLE_NAME);
        String query= builder.buildQuery(projection,selection,null,null,null,null);

        Cursor cursor = db.getOpenHelper().getReadableDatabase().query(query);
        return (cursor);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}