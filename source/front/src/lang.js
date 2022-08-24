import i18n from "i18next";
import {initReactI18next } from "react-i18next";

const languagesList = {
  en: {
    translation: {
      "Target Feature set": "Target Feature set",
      "Features Category": "Features Category",
      "search": "Search",
      "search keyword":"Search Keyword",
      "Target Feature Set Register":"Target Feature Set Register"
    }
  },
  ko: {
    translation: {
      "Target Feature set": "타겟 피처 세트",
      "Features Category": "피처 카테고리",
      "search": "검색",
      "search keyword":"한국어",
      "Target Feature Set Register":"타겟 피처세트 등록"
    }
  },
  jp:{
    translation: {
      "Target Feature set": "ターゲット機能セット",
      "Features Category": "機能カテゴリ",
      "search": "探す",
      "search keyword":"検索語",
      "Target Feature Set Register":"ターゲット機能セット登録"
    }
  }
}

i18n
  .use(initReactI18next)
  .init({
    resources: languagesList,
    lng: "en", 
    fallbackLng: "en",
  });