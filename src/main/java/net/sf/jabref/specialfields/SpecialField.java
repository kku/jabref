package net.sf.jabref.specialfields;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.swing.Icon;

import net.sf.jabref.logic.l10n.Localization;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class SpecialField {

    private static final Log LOGGER = LogFactory.getLog(SpecialField.class);

    // currently, menuString is used for undo string
    // public static String TEXT_UNDO;

    private List<SpecialFieldValue> values;
    private List<String> keywords;
    private HashMap<String, SpecialFieldValue> map;


    protected void setValues(List<SpecialFieldValue> values) {
        this.values = values;
        this.keywords = new ArrayList<>();
        this.map = new HashMap<>();
        for (SpecialFieldValue v : values) {
            v.getKeyword().ifPresent(keywords::add);
            v.getFieldValue().ifPresent(fieldValue -> map.put(fieldValue, v));
        }
    }

    public List<SpecialFieldValue> getValues() {
        return this.values;
    }

    public List<String> getKeyWords() {
        return this.keywords;
    }

    public Optional<SpecialFieldValue> parse(String s) {
        return Optional.ofNullable(map.get(s));
    }

    public abstract String getFieldName();

    public abstract String getLocalizedFieldName();

    public abstract Icon getRepresentingIcon();

    public String getMenuString() {
        return getLocalizedFieldName();
    }

    public String getToolTip() {
        return getLocalizedFieldName();
    }

    public String getTextDone(String... params) {
        Objects.requireNonNull(params);

        if (isSingleValueField() && (params.length == 1) && (params[0] != null)) {
            // Single value fields can be toggled only
            return Localization.lang("Toggled '%0' for %1 entries", getLocalizedFieldName(), params[0]);
        } else if (!isSingleValueField() && (params.length == 2) && (params[0] != null) && (params[1] != null)) {
            // setting a multi value special field - the setted value is displayed, too
            String[] allParams = {getLocalizedFieldName(), params[0], params[1]};
            return Localization.lang("Set '%0' to '%1' for %2 entries", allParams);
        } else if (!isSingleValueField() && (params.length == 1) && (params[0] != null)) {
            // clearing a multi value specialfield
            return Localization.lang("Cleared '%0' for %1 entries", getLocalizedFieldName(), params[0]);
        } else {
            // invalid usage
            LOGGER.info("Creation of special field status change message failed: illegal argument combination.");
            return "";
        }
    }

    public boolean isSingleValueField() {
        return false;
    }

}
