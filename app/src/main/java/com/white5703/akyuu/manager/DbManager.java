package com.white5703.akyuu.manager;

import com.white5703.akyuu.app.AkyuuApplication;
import com.white5703.akyuu.dao.NoteDao;
import com.white5703.akyuu.entity.Note;
import com.white5703.akyuu.util.CommonUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.greenrobot.greendao.query.Query;



public class DbManager {

    private static NoteDao mNoteDao = AkyuuApplication.getInstance().getDaoSession().getNoteDao();

    public static void insertNote(String brief, String detail, String tag, int priority,
        String reference, int briefgravity, int brieffontsize,
        int detailgravity, int detailfontsize, List<String> latexs) {
        Note note = CommonUtils.getDefaultNote();
        note.setBrief(brief);
        note.setDetail(detail);
        note.setPriority(priority);
        note.setTag(tag);
        note.setUpdatetime(new Date());
        note.setReference(reference);
        note.setBriefgravity(briefgravity);
        note.setBrieffontsize(brieffontsize);
        note.setDetailgravity(detailgravity);
        note.setDetailfontsize(detailfontsize);
        note.setLatexs(latexs);
        mNoteDao.insert(note);
    }

    public static void insertDefaultNote() {
        mNoteDao.insert(CommonUtils.getDefaultNote());
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

    public static Note getNote(long id) {
        return mNoteDao.load(id);
    }

    public static Note getLatestNote() {
        List<Note> allNote = mNoteDao.loadAll();
        return allNote.get(allNote.size() - 1);
    }

    public static List getNoteList(String tag) {
        if (tag.equalsIgnoreCase("Any")) {
            return mNoteDao.loadAll();
        }

        Query query = mNoteDao.queryBuilder().where(NoteDao.Properties.Tag.eq(tag)).build();
        return query.list();
    }

    public static void updateNote(Note note) {
        mNoteDao.update(note);
    }

    public static void clearTableNote() {
        mNoteDao.deleteAll();
    }

    @SuppressWarnings("unchecked")
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
