package com.white5703.akyuu.manager;

import com.white5703.akyuu.app.AkyuuApplication;
import com.white5703.akyuu.dao.NoteDao;
import com.white5703.akyuu.entity.Note;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.greendao.query.Query;



public class DbManager {

    private static NoteDao mNoteDao = AkyuuApplication.getInstance().getDaoSession().getNoteDao();

    public static void insertNote(String content,String hided,String tag,int priority) {
        Note note = new Note();
        note.setContent(content);
        note.setHided(hided);
        note.setPriority(priority);
        note.setTag(tag);
        mNoteDao.insert(note);
    }

    public static void deleteNote(long id) {
        mNoteDao.deleteByKey(id);
    }

    public static void increasePriority(long id) {
        Note note = mNoteDao.loadByRowId(id);
        note.setPriority(note.getPriority() + 1);
        mNoteDao.save(note);
    }

    public static void decreasePriority(long id) {
        Note note = mNoteDao.loadByRowId(id);
        note.setPriority(note.getPriority() - 1);
        mNoteDao.save(note);
    }

    public static long getNoteCount() {
        return mNoteDao.count();
    }

    public static List getNoteList(String tag) {
        if (tag.equalsIgnoreCase("Any")) {
            return mNoteDao.loadAll();
        }

        Query query = mNoteDao.queryBuilder().where(NoteDao.Properties.Tag.eq(tag)).build();
        return query.list();
    }

    public static void clearTableNote() {
        mNoteDao.deleteAll();
    }

    public static List<String> getTagList() {
        List<String> listTag = new ArrayList<>();
        listTag.add("Any");
        List<Note> allNote = getNoteList("Any");
        for (int i = 0; i < allNote.size(); i++) {
            String tag = allNote.get(i).getTag();
            if (!listTag.contains(tag)) {
                listTag.add(tag);
            }
        }
        return listTag;
    }


}