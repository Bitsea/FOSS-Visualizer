/*
 * FOSS Visualizer
 * Copyright (C) 2025 Bitsea GmbH
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.fossvisualizer.info;


import java.util.List;

public class FossInfo {
    String issue;
    String swArea;
    String name;
    String parentName;
    String componentIndicator;
    int priority;
    String licenseType;
    String licenseText;
    String description;
    String packageFileVersion;
    String url;
    String externalNotes;
    String vulnerabilities;
    String vulnerabilityList;
    int fileNumber;
    String files;
    String copyright;
    String modification;
    LinkingType linkingType;
    String encryption;
    int activity;
    int size;
    int communityDiversity;
    String mostActualVersion;
    String approvedByPolicy;
    LicenseType strictness;
    boolean patent;
    boolean exportRestriction;
    boolean ki;
    boolean snippet;
    int numberOfExportRestriction;
    int numberOfVulnerabilities;

    public FossInfo(List<String> infos){
        this.issue = infos.get(0);
        this.swArea = infos.get(1);
        this.name = infos.get(2);
        this.parentName = infos.get(3);
        this.componentIndicator = infos.get(4);
        this.priority = (int)Float.parseFloat(infos.get(5));
        this.licenseType = infos.get(6);
        this.licenseText = infos.get(7);
        this.description = infos.get(8);
        this.packageFileVersion = infos.get(9);
        this.url = infos.get(10);
        this.externalNotes = infos.get(11);
        this.vulnerabilities = infos.get(12);
        this.vulnerabilityList = infos.get(13);
        this.fileNumber = (int)Float.parseFloat(infos.get(14));
        this.files = infos.get(15);
        this.copyright = infos.get(16);
        this.modification = infos.get(17);
        if (infos.get(18).equalsIgnoreCase("Static")){
            this.linkingType = LinkingType.STATIC;
        } else if (infos.get(18).equalsIgnoreCase("Dynamic")) {
            this.linkingType = LinkingType.DYNAMIC;
        }
        this.encryption = infos.get(19);
        this.activity = (int)Float.parseFloat(infos.get(20));
        this.size = (int)Float.parseFloat(infos.get(21));
        this.communityDiversity = (int)Float.parseFloat(infos.get(22));
        this.mostActualVersion = infos.get(23);
        this.approvedByPolicy = infos.get(24);
        this.patent = infos.get(25).equalsIgnoreCase("yes");
        this.exportRestriction = infos.get(26).equalsIgnoreCase("yes");
        this.numberOfExportRestriction = (int)Float.parseFloat(infos.get(27));
        this.numberOfVulnerabilities = (int)Float.parseFloat(infos.get(28));
        this.ki = infos.get(29).equalsIgnoreCase("yes");
        this.snippet = infos.get(30).equalsIgnoreCase("yes");
        String strictness = infos.get(31);
        if(strictness.equalsIgnoreCase("copyleft")){
            this.strictness = LicenseType.COPYLEFT;
        }else if(strictness.equalsIgnoreCase("permissive")){
            this.strictness = LicenseType.PERMISSIVE;
        } else {
            this.strictness = LicenseType.OTHER;
        }
    }

    public String getIssue() {
        return issue;
    }

    public String getSwArea() {
        return swArea;
    }

    public String getName() {
        return name;
    }

    public String getParentName() {
        return parentName;
    }

    public String getComponentIndicator() {
        return componentIndicator;
    }

    public int getPriority() {
        return priority;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public String getLicenseText() {
        return licenseText;
    }

    public String getDescription() {
        return description;
    }

    public String getPackageFileVersion() {
        return packageFileVersion;
    }

    public String getUrl() {
        return url;
    }

    public String getExternalNotes() {
        return externalNotes;
    }

    public String getVulnerabilities() {
        return vulnerabilities;
    }

    public String getVulnerabilityList() {
        return vulnerabilityList;
    }

    public int getFileNumber() {
        return fileNumber;
    }

    public String getFiles() {
        return files;
    }

    public String getCopyright() {
        return copyright;
    }

    public String getModification() {
        return modification;
    }

    public LinkingType getLinkingType() {
        return linkingType;
    }

    public String getEncryption() {
        return encryption;
    }

    public int getActivity() {
        return activity;
    }

    public int getSize() {
        return size;
    }

    public int getCommunityDiversity() {
        return communityDiversity;
    }

    public String getMostActualVersion() {
        return mostActualVersion;
    }

    public String getApprovedByPolicy() {
        return approvedByPolicy;
    }

    public LicenseType getStrictness(){
        return strictness;
    }

    public  boolean getPatent(){return patent;}

    public boolean getExportRestriction(){return exportRestriction;}

    public  int getNumberOfExportRestriction(){return numberOfExportRestriction;}

    public int getNumberOfVulnerabilities(){return numberOfVulnerabilities;}

    public boolean getKi(){return ki;}

    public boolean getSnippet(){return snippet;}
}