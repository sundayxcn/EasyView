package com.sunday.easy.easyview;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class EasyEdit extends RelativeLayout {
    private EditText editText;
    private ImageView ivClearIV;
    private ImageView ivShowPwdIV;
    private String hint;
    private int intPutType;
    private boolean isShowClear;

    private boolean isShowViewPassword;

    private boolean isShowPassword;

    private ArrayList<TextChangeListener> mListeners;

    public EasyEdit(Context context) {
        this(context, null);
    }

    public EasyEdit(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EasyEdit(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.EasyEdit);
        hint = a.getString(R.styleable.EasyEdit_easy_hint);
        intPutType = a.getInt(R.styleable.EasyEdit_easy_inputType, EditorInfo.TYPE_CLASS_TEXT);
        isShowClear = a.getBoolean(R.styleable.EasyEdit_easy_isShowClear, true);
        isShowViewPassword = a.getBoolean(R.styleable.EasyEdit_easy_isViewPassword, true);
        int backgroundId = a.getResourceId(R.styleable.EasyEdit_easy_background, R.drawable.selector_bg);
        a.recycle();

        View parent = LayoutInflater.from(context).inflate(R.layout.layout_edit, this);


        editText = parent.findViewById(R.id.edit_text);
        if (!TextUtils.isEmpty(hint)) {
            editText.setHint(hint);
        }
        editText.setInputType(intPutType);
        if(intPutType == EditorInfo.TYPE_TEXT_VARIATION_PASSWORD){
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        editText.setBackgroundResource(backgroundId);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();

                if(intPutType ==  EditorInfo.TYPE_TEXT_VARIATION_PASSWORD) {
                    if (TextUtils.isEmpty(text)) {
                        ivShowPwdIV.setVisibility(GONE);
                    } else{

                        if(isShowViewPassword) {
                            ivShowPwdIV.setVisibility(VISIBLE);
                        }
                    }
                }else{
                    if (TextUtils.isEmpty(text)) {
                        ivClearIV.setVisibility(GONE);
                    } else {
                        if(isShowClear) {
                            ivClearIV.setVisibility(VISIBLE);
                        }
                    }
                }

                if(mListeners != null){
                    for(TextChangeListener listener : mListeners){
                        listener.onTextChange(text);
                    }
                }
            }
        });

        ivClearIV = findViewById(R.id.clear);
        ivClearIV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });

        ivShowPwdIV = findViewById(R.id.pwd);
        ivShowPwdIV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowPassword) {
                    isShowPassword = false;
                    ivShowPwdIV.setImageResource(R.mipmap.ic_hide_pwd);
                    editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    editText.setSelection(editText.getText().length());
                } else {
                    ivShowPwdIV.setImageResource(R.mipmap.ic_show_pwd);
                    editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    editText.setSelection(editText.getText().length());
                    isShowPassword = true;
                }
            }
        });

    }


    public String getText() {
        return editText.getText().toString().trim();
    }

    public boolean isEmpty() {
        return TextUtils.isEmpty(getText());
    }

    public void addTextChangeListener(TextChangeListener textChangeListener) {
        if(mListeners == null){
            mListeners = new ArrayList<>();
        }
        mListeners.add(textChangeListener);
    }

    /**
     * Removes the specified TextWatcher from the list of those whose
     * methods are called
     * whenever this TextView's text changes.
     */
    public void removeTextChangedListener(TextChangeListener listener) {
        if (mListeners != null) {
            int i = mListeners.indexOf(listener);

            if (i >= 0) {
                mListeners.remove(i);
            }
        }
    }


    public interface TextChangeListener {
        void onTextChange(String text);
    }


    public void setText(String text){
        editText.setText(text);
    }



}
