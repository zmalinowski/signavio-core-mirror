/**
 * Copyright (c) 2009, Signavio GmbH
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
/**
 * 
 */
package com.signavio.platform.core.impl;

import com.signavio.platform.core.PlatformProperties;
import com.signavio.platform.exceptions.InitializationException;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * Read the properties from the web.xml file
 * @author Bjoern Wagner
 *
 */
public class FsPlatformPropertiesImpl implements PlatformProperties {
	private String serverName;
	private String platformUri;
	private String explorerUri;
	private String editorUri;
	private String libsUri;
	private String supportedBrowserEditor;
	
	private String rootDirectoryPath;
	
	private String aperteStepEditorUrl;
	private String aperteQueueEditorUrl;
	private String aperteActionEditorUrl;
	private String aperteStepListUrl;
	private String aperteButtonListUrl;
	private String aperteOsgiPluginsDir;
	private String jbpmGuiUrl;
	

	public FsPlatformPropertiesImpl(ServletContext context) {
		supportedBrowserEditor = context.getInitParameter("supportedBrowserEditor");
		
		Properties props = new Properties();
		try {
			props.load(this.getClass().getClassLoader().getResourceAsStream("configuration.properties"));
		} catch (IOException e) {
			throw new InitializationException(e);
		}
		
		String tempRootDirectoryPath = props.getProperty("fileSystemRootDirectory");
        if (tempRootDirectoryPath != null && !tempRootDirectoryPath.trim().isEmpty()) {
            if (tempRootDirectoryPath.endsWith(File.separator)) {
                rootDirectoryPath = tempRootDirectoryPath.substring(0, tempRootDirectoryPath.length()-1);
            } else {
                rootDirectoryPath = tempRootDirectoryPath;
            }
        } else {
            // TODO figure out how to import maven dependencies from rest of modules so getHome() won't be duplicated
            rootDirectoryPath = getHomePath() + File.separator + "modeler-repo";
        }

		serverName = props.getProperty("host");
        if (serverName == null) {
            serverName = "http://localhost:8080/";
        }
		platformUri = context.getContextPath() + "/p";
		explorerUri = context.getContextPath() + "/explorer";
		editorUri = context.getContextPath() + "/editor";
		libsUri = context.getContextPath() + "/libs";
		
		aperteStepEditorUrl = props.getProperty("aperteStepEditorUrl");
		aperteQueueEditorUrl = props.getProperty("aperteQueueEditorUrl");
		aperteActionEditorUrl = props.getProperty("aperteActionEditorUrl");
		aperteStepListUrl = props.getProperty("aperteStepListUrl");
		aperteButtonListUrl = props.getProperty("aperteButtonListUrl");
		
		String tempAperteOsgiPluginsDir = props.getProperty("aperteOsgiPluginsDir");
		if (tempAperteOsgiPluginsDir != null && !tempAperteOsgiPluginsDir.trim().isEmpty()) {
			if (tempAperteOsgiPluginsDir.endsWith(File.separator)) {
			  aperteOsgiPluginsDir = tempAperteOsgiPluginsDir.substring(0, tempAperteOsgiPluginsDir.length()-1);
			} else {
			  aperteOsgiPluginsDir = tempAperteOsgiPluginsDir;
			}
		} else {
			// TODO figure out how to import maven dependencies from rest of modules so getHome() won't be duplicated
            aperteOsgiPluginsDir = getHomePath() + File.separator + "osgi-plugins";
		}
		jbpmGuiUrl = props.getProperty("jbpmGuiUrl");
	}

    public static String getHomePath() {

        String[] propertyNames = {
                "aperte.workflow.home",
                "liferay.home",
                "catalina.home",
                "jboss.home.dir",
        };
        for (String propertyName : propertyNames) {
            String v = System.getProperty(propertyName);
            if (v != null)
                return v;
        }
        return "";
    }
	
	/* (non-Javadoc)
	 * @see com.signavio.platform.core.impl.PlatformProperties#getServerName()
	 */
	public String getServerName() {
		return serverName;
	}
	/* (non-Javadoc)
	 * @see com.signavio.platform.core.impl.PlatformProperties#getPlatformUri()
	 */
	public String getPlatformUri() {
		return platformUri;
	}
	/* (non-Javadoc)
	 * @see com.signavio.platform.core.impl.PlatformProperties#getExplorerUri()
	 */
	public String getExplorerUri() {
		return explorerUri;
	}
	/* (non-Javadoc)
	 * @see com.signavio.platform.core.impl.PlatformProperties#getEditorUri()
	 */
	public String getEditorUri() {
		return editorUri;
	}
	/* (non-Javadoc)
	 * @see com.signavio.platform.core.impl.PlatformProperties#getLibsUri()
	 */
	public String getLibsUri() {
		return libsUri;
	}	
	/* (non-Javadoc)
	 * @see com.signavio.platform.core.impl.PlatformProperties#getSupportedBrowserEditorRegExp()
	 */
	public String getSupportedBrowserEditorRegExp() {
		return supportedBrowserEditor;
	}

	public Set<String> getAdmins() {
		return new HashSet<String>(0);
	}
	
	public String getRootDirectoryPath() {
		return rootDirectoryPath;
	}
	
	public String getAperteStepEditorUrl() {
		return aperteStepEditorUrl;
	}
	
	public String getAperteQueueEditorUrl() {
		return aperteQueueEditorUrl;
	}
	
	public String getAperteActionEditorUrl() {
		return aperteActionEditorUrl;
	}
	
	public String getAperteStepListUrl() {
		return aperteStepListUrl;
	}
	
	public String getAperteButtonListUrl() {
		return aperteButtonListUrl;
	}
	
	public String getAperteOsgiPluginsDir() {
		return aperteOsgiPluginsDir;
	}
	
	public String getJbpmGuiUrl() {
		return jbpmGuiUrl;
	}
}
