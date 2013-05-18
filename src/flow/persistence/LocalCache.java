package flow.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import flow.activity.R;
import flow.entry.Site;

public class LocalCache extends SQLiteOpenHelper {

    private static final String DATABASE = "cache";
    private static final int VERSION = 1;
    private static final String TABLE_NAME = "sites";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_SITE_TYPE = "sitetype";
    private static final String COLUMN_API_SITE_PARAMETER = "apisiteparameter";
    private static final String COLUMN_NAME = "name";


    private static String[] INITIAL_SITES;

    private static LocalCache instance = null;

    private LocalCache(Context context) {

        super(context, DATABASE, null, VERSION);

        if (context != null) {

            INITIAL_SITES = context.getResources().getStringArray(R.array.initial_sites);


        } else {
            INITIAL_SITES = null;

        }

    }

    public static synchronized LocalCache getInstance(Context context) {

        if (instance == null) {

            instance = new LocalCache(context);

        }

        return instance;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        StringBuilder sql = new StringBuilder();

        sql.append("CREATE TABLE ");
        sql.append(TABLE_NAME);
        sql.append("(").append(COLUMN_ID)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT,")
                .append(COLUMN_SITE_TYPE).append(" TEXT,");
        sql.append(COLUMN_NAME).append(" TEXT NOT NULL UNIQUE,");
        sql.append(COLUMN_API_SITE_PARAMETER).append(" TEXT)");

        db.execSQL(sql.toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

    public void updateSites(Site[] sites) {


        if (sites != null && sites.length > 0) {

            ContentValues values = new ContentValues();

            SQLiteDatabase db = getWritableDatabase();

            String where = COLUMN_NAME + "=?";
            String[] arg = new String[1];

            for (Site site : sites) {

                if (site.siteType == null || site.siteType.equals("")
                        || site.name == null || site.name.equals("")
                        || site.apiSiteParameter == null
                        || site.apiSiteParameter.equals("")) {

                    continue;

                }

                values.put(COLUMN_SITE_TYPE, site.siteType);
                values.put(COLUMN_NAME, site.name);
                values.put(COLUMN_API_SITE_PARAMETER, site.apiSiteParameter);

                arg[0] = site.name;

                db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);


            }

        }

    }

    public void deleteSites(Site[] sites) {

        if (sites != null && sites.length > 0) {


            for (Site site : sites) {


                if (site != null && site.name != null && !site.name.equals("")) {

                    SQLiteDatabase db = getWritableDatabase();
                    String where = COLUMN_NAME + "=?";
                    String[] arg = new String[1];
                    arg[0] = site.name;

                    db.delete(TABLE_NAME, where, arg);

                }

            }

        }


    }

    public boolean initSites() {


        Site[] sites = new Site[7];

        updateSites(sites);

        return false;


    }


    public Site[] selectSites() {

        SQLiteDatabase db = getReadableDatabase();

        String[] columns = new String[]{COLUMN_NAME,
                COLUMN_SITE_TYPE,
                COLUMN_API_SITE_PARAMETER};

        Cursor c = db.query(TABLE_NAME, columns, null, null, null, null, null);

        if (c != null && c.getCount() > 0) {

            Site[] sites = new Site[c.getCount()];

            for (int i = 0; i < c.getCount(); i++) {

                Site site = new Site();
                c.moveToPosition(i);
                site.name = c.getString(0);
                site.siteType = c.getString(1);
                site.apiSiteParameter = c.getString(2);

                sites[i] = site;

            }

            c.close();

            return sites;

        }

        if (c != null) {

            c.close();

        }


        return null;

    }
}
