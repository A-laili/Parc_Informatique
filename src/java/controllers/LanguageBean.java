/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author Asmaa
 */
@ManagedBean(name = "languageBean")
@SessionScoped
public class LanguageBean {

    private String localeCode = "en"; // Default locale
    private static Map<String, Object> countries;

    static {
        countries = new LinkedHashMap<String, Object>();
        countries.put("English", Locale.ENGLISH);
        countries.put("French", Locale.FRENCH);
    }

    public String getLocaleCode() {
        return localeCode;
    }

    public void setLocaleCode(String localeCode) {
        this.localeCode = localeCode;
    }

    public void changeLocale() {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(localeCode));
    }

    public Map<String, Object> getCountries() {
        return countries;
    }

    public  void setCountries(Map<String, Object> countries) {
        LanguageBean.countries = countries;
    }

    public void countryLocalChanged(ValueChangeEvent take_event) {
        String new_lang = take_event.getNewValue().toString();
        for (Map.Entry<String, Object> entry : countries.entrySet()) {
            if (entry.getValue().toString().equals(new_lang)) {
                FacesContext.getCurrentInstance().getViewRoot().setLocale((Locale) entry.getValue());
            }

        }
    }

}
