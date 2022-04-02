package com.example.android.miwok;

public class Word {
    //    Add the English translation variable
    private String mEnglishTranslation;

    //    Add the Miwok traslation variable
    private String mMiwokTranslation;

    //    image provided variable
    private final int NO_IMAGE_PROVIDED = -1;

    //    Add the image variable
    private int mImageResourceId = NO_IMAGE_PROVIDED;

    //    Add variable to store audio resource id
    private int mAudioResourceId;


    //    Constructor method for word class
    //Another constructor method
    public Word(String englishWord, String miwokWord, int audioId) {
        this.mEnglishTranslation = englishWord;
        this.mMiwokTranslation = miwokWord;
        this.mAudioResourceId = audioId;
    }

    public Word(String englishWord, String miwokWord, int imageId,int audioId) {
        this.mEnglishTranslation = englishWord;
        this.mMiwokTranslation = miwokWord;
        this.mImageResourceId = imageId;
        this.mAudioResourceId = audioId;
    }


    //    getter method for englishWord
    public String getmEnglishTranslation() {
        return this.mEnglishTranslation;
    }

    //    getter method for miwokWord
    public String getmMiwokTranslation() {
        return this.mMiwokTranslation;
    }

//    getter method for image

    public int getmImageResourceId() {
        return this.mImageResourceId;
    }
//    getter method for audio
    public int getmAudioResourceId(){return this.mAudioResourceId;}

    //    function which returns true if image is initialized
    public boolean hasImage() {
        return this.mImageResourceId != this.NO_IMAGE_PROVIDED;
    }


}

