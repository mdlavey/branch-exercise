package com.exercises.githubdata.client.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubRepo {

    String name;
    @JsonProperty("html_url")
    String htmlUrl;

    public String getName () {
        return name;
    }

    public void setName (String name){
        this.name = name;
    }

    public String getHtmlUrl () {
        return htmlUrl;
    }

    public void setHtmlUrl (String htmlUrl){
        this.htmlUrl = htmlUrl;
    }
}
