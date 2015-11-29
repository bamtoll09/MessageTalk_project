package zerofox.kr.ttak;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 재현 on 2015-11-28.
 */
public class ListViewAdapter extends BaseAdapter {

    private Context mContext = null;
    private ArrayList<ListData> mListData = new ArrayList<ListData>();

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public Object getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        final Context context = parent.getContext();

        CircleImageView circleImageView;
        TextView title, text, date;
        CustomHolder holder;

        // 리스트가 길어지면서 현재 화면에 보이지 않는 아이템은 converView가 null인 상태로 들어 옴
        if ( convertView == null ) {
            // view가 null일 경우 커스텀 레이아웃을 얻어 옴
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.room_item, parent, false);

            circleImageView = (CircleImageView) convertView.findViewById(R.id.icon);

            // TextView에 현재 position의 문자열 추가
            title = (TextView) convertView.findViewById(R.id.title);
            text = (TextView) convertView.findViewById(R.id.text);
            date = (TextView) convertView.findViewById(R.id.date);

            circleImageView.setImageDrawable(mListData.get(pos).mIcon);
            title.setText(mListData.get(pos).mTitle);
            text.setText(mListData.get(pos).mText);
            date.setText(mListData.get(pos).mDate);

            holder = new CustomHolder();
            holder.mCIV = circleImageView;
            holder.mTitle = title;
            holder.mText = text;
            holder.mDate = date;
        }

        return convertView;
    }

    private class CustomHolder {
        CircleImageView mCIV;
        TextView mTitle, mText, mDate;
    }

    public void add(Drawable drawable, String mTitle, String mText, String mDate) {
        ListData listData = new ListData(drawable, mTitle, mText, mDate);
        mListData.add(listData);
    }

    public void remove(int position) {
        mListData.remove(position);
    }

    public void changeIcon(int position, Drawable drawable) {
        mListData.get(position).mIcon = drawable;
    }

    public void changeTitle(int position, String mTitle) {
        mListData.get(position).mTitle = mTitle;
    }

    public void changeText(int position, String mText) {
        mListData.get(position).mText = mText;
    }

    public void changeDate(int position, String mDate) {
        mListData.get(position).mDate = mDate;
    }
}
