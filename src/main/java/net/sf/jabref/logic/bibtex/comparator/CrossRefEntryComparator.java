package net.sf.jabref.logic.bibtex.comparator;

import java.util.Comparator;

import net.sf.jabref.model.entry.BibEntry;
import net.sf.jabref.model.entry.FieldName;

/**
 * Compares Bibtex entries based on their 'crossref' fields. Entries including
 * this field are deemed smaller than entries without this field. This serves
 * the purpose of always placing referenced entries after referring entries in
 * the .bib file. After this criterion comes comparisons of individual fields.
 */
public class CrossRefEntryComparator implements Comparator<BibEntry> {

    @Override
    public int compare(BibEntry e1, BibEntry e2) {

        Boolean b1 = e1.hasField(FieldName.CROSSREF);
        Boolean b2 = e2.hasField(FieldName.CROSSREF);

        if ((!b1) && (!b2)) {
            return 0; // secComparator.compare(e1, e2);
        }
        if (b1 && b2) {
            return 0; // secComparator.compare(e1, e2);
        }
        if (!b1) {
            return 1;
        } else {
            return -1;
        }
    }
}
