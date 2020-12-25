package recycler_view;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable {
    private String surname;
    private String name;
    private String patronymic;

    public Student(String surname, String name, String patronymic) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
    }

    protected Student(Parcel in) {
        surname = in.readString();
        name = in.readString();
        patronymic = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(surname);
        dest.writeString(name);
        dest.writeString(patronymic);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getPatronymic() {
        return patronymic;
    }
}
