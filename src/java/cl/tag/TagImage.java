/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.tag;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 *
 * @author roman
 */
public class TagImage extends SimpleTagSupport {

    private Object array;
    private String tam;

    /**
     * Called by the container to invoke this tag. The implementation of this
     * method is provided by the tag library developer, and handles all tag
     * processing, body iteration, etc.
     */
    @Override
    public void doTag() throws JspException {
        JspWriter out = getJspContext().getOut();
        
        try {
            // TODO: insert code to write html before writing the body content.
            // e.g.:
            //
            // out.println("<strong>" + attribute_1 + "</strong>");
            // out.println("    <blockquote>");

            JspFragment f = getJspBody();
            if (f != null) {
                f.invoke(out);
            }

            // TODO: insert code to write html after writing the body content.
            // e.g.:
            //
            // out.println("    </blockquote>");
            //out.print("<td><img class='materialboxed' width='50' src='data:image/*;base64," + Base64.encode(data) + "'/></td>");
            byte [] data = (byte[]) array;
             out.println("<img class='materialboxed' width='"+tam+"' src='data:image/*;base64,"+Base64.encode(data)+"' />");
            
            //import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
            
        } catch (java.io.IOException ex) {
            throw new JspException("Error in TagImage tag", ex);
        }
    }

    public void setArray(Object array) {
        this.array = array;
    }

    public void setTam(String tam) {
        this.tam = tam;
    }
    
}
