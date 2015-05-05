package br.com.herediadesign.pecs.backup;

import android.app.backup.BackupAgentHelper;
import android.app.backup.BackupDataInput;
import android.app.backup.BackupDataOutput;
import android.app.backup.BackupManager;
import android.app.backup.FileBackupHelper;
import android.content.Context;
import android.os.ParcelFileDescriptor;

import java.io.IOException;

import br.com.herediadesign.pecs.db.helper.PecsDbHelper;

/**
 * Created by aheredia on 5/5/2015.
 */
public class PecsBackupAgent extends BackupAgentHelper {

    // A key to uniquely identify the set of backup data
    static final String PECS_BACKUP_KEY = "pecs_files";

    @Override
    public void onCreate(){
        FileBackupHelper dbFile = new FileBackupHelper(this, "../databases/" + PecsDbHelper.DATABASE_NAME);
        addHelper(PecsDbHelper.DATABASE_NAME, dbFile);

        String[] files = getFilesDir().list();
        FileBackupHelper pecsFiles = new FileBackupHelper(this, files);
        addHelper(PECS_BACKUP_KEY, pecsFiles);
    }

    public void requestBackup(Context ctx) {
        BackupManager bmg = new BackupManager(ctx);
        bmg.dataChanged();
    }

    @Override
    public void onBackup(ParcelFileDescriptor oldState, BackupDataOutput data,
                         ParcelFileDescriptor newState) throws IOException {
        synchronized (PecsDbHelper.dbLock) {
            super.onBackup(oldState, data, newState);
        }
    }

    @Override
    public void onRestore(BackupDataInput data, int appVersionCode,
                          ParcelFileDescriptor newState) throws IOException {
        synchronized (PecsDbHelper.dbLock) {
            super.onRestore(data, appVersionCode, newState);
        }
    }
}
