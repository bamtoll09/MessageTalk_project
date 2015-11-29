package zerofox.kr.ttak;

import android.graphics.drawable.Drawable;

/**
 * Created by 재현 on 2015-11-28.
 */
public class ListData {

    //톡방 아이콘
    public Drawable mIcon;

    //톡방 제목
    public String mTitle;

    //마지막 대화
    public String mText;

    //마지막 대화 날짜
    public String mDate;

    public ListData(Drawable drawable, String mTitle, String mText, String mDate) {
        this.mIcon = drawable;
        this.mTitle = mTitle;
        this.mText = mText;
        this.mDate = mDate;
    }
}
