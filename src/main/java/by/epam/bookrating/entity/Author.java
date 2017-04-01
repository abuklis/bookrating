package by.epam.bookrating.entity;

public class Author{
    private long authorId;
    private String fullName;
    private int birthYear;
    private String birthCountry;
    private String biography;
    private String imageUrl;

    public Author() {
    }

    public Author(long authorId, String fullName, int birthYear, String birthCountry, String biography, String imageUrl) {
        this.authorId = authorId;
        this.fullName = fullName;
        this.birthCountry = birthCountry;
        this.birthYear = birthYear;
        this.biography = biography;
        this.imageUrl = imageUrl;
    }

    public Author(String fullName, int birthYear, String birthCountry, String biography) {
        this.biography = biography;
        this.birthCountry = birthCountry;
        this.birthYear = birthYear;
        this.fullName = fullName;
    }

    public Author(String fullName, int birthYear, String birthCountry, String biography, String imageUrl) {
        this.biography = biography;
        this.birthCountry = birthCountry;
        this.birthYear = birthYear;
        this.fullName = fullName;
        this.imageUrl = imageUrl;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getBirthCountry() {
        return birthCountry;
    }

    public void setBirthCountry(String birthCountry) {
        this.birthCountry = birthCountry;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Author{" +
                "authorId=" + authorId +
                ", fullName='" + fullName + '\'' +
                ", birthYear=" + birthYear +
                ", birthCountry='" + birthCountry + '\'' +
                ", biography='" + biography + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

}
