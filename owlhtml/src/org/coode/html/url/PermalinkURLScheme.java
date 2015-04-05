/*
* Copyright (C) 2007, University of Manchester
*/
package org.coode.html.url;

import java.net.MalformedURLException;
import java.net.URL;

import org.coode.html.OWLHTMLKit;
import org.coode.html.impl.OWLHTMLConstants;
import org.coode.html.impl.OWLHTMLParam;
import org.coode.owl.mngr.NamedObjectType;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntology;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author: Nick Drummond<br>
 * http://www.cs.man.ac.uk/~drummond/<br><br>
 * <p/>
 * The University Of Manchester<br>
 * Bio Health Informatics Group<br>
 * Date: Jan 17, 2008<br><br>
 *
 * Wraps a give URLScheme, but provides an additional parameter for the permalink
 */
public class PermalinkURLScheme implements URLScheme {

    private static final Logger logger = LoggerFactory
            .getLogger(PermalinkURLScheme.class);

    private URLScheme baseScheme;


    public PermalinkURLScheme(URLScheme baseScheme) {
        this.baseScheme = baseScheme;
    }

    private URL append(URL url) {
        if (getOWLHTMLKit().getCurrentLabel() != null){
            String link = url.toString();
            if (!link.contains(OWLHTMLConstants.START_QUERY)){
                link += OWLHTMLConstants.START_QUERY;
            }
            else{
                link += OWLHTMLConstants.PARAM_SEP;
            }
            link += OWLHTMLParam.session + OWLHTMLConstants.EQUALS + getOWLHTMLKit().getCurrentLabel();

            try {
                return new URL(link);
            }
            catch (MalformedURLException e) {
                logger.error("Cannot create permalink URL", e);
            }
        }
        return url;
    }

    @Override
    public URL getURLForOWLObject(OWLObject entity) {
        return append(baseScheme.getURLForOWLObject(entity));
    }

    @Override
    public OWLObject getOWLObjectForURL(URL url) {
        return baseScheme.getOWLObjectForURL(url);
    }

    @Override
    public OWLHTMLKit getOWLHTMLKit() {
        return baseScheme.getOWLHTMLKit();
    }

    @Override
    public NamedObjectType getType(URL url) {
        return baseScheme.getType(url);
    }

    @Override
    public URL getBaseURL() {
        return baseScheme.getBaseURL();
    }

    @Override
    public URL getURLForOntologyIndex(OWLOntology ont, NamedObjectType type) {
        return append(baseScheme.getURLForOntologyIndex(ont, type));
    }

    @Override
    public void setAdditionalLinkArguments(String s) {
        baseScheme.setAdditionalLinkArguments(s);
    }

    @Override
    public void clearAdditionalLinkArguments() {
        baseScheme.clearAdditionalLinkArguments();
    }

    @Override
    public URL getURLForIndex(NamedObjectType type) {
        return append(baseScheme.getURLForIndex(type));
    }

    @Override
    public URL getURLForRelativePage(String pageRelativeToBase) {
        return append(baseScheme.getURLForRelativePage(pageRelativeToBase));
    }

    @Override
    public URL getURLForAbsolutePage(URL pageURL) {
        return append(baseScheme.getURLForAbsolutePage(pageURL));
    }
}
