package com.softdesign.devintensive.data.network.res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UserListRes {
        @SerializedName("success")
        @Expose
        private boolean success;
        @SerializedName("data")
        @Expose
        private List<UserData> data =  new ArrayList<UserData>();
        // public Data getData(){ return data;}



        public List<UserData> getData(){
            return data;
        }

        public class UserData{
            @SerializedName("_id")
            @Expose
            private String id;
            @SerializedName("first_name")
            @Expose
            private String firstName;
            @SerializedName("second_name")
            @Expose
            private String secondName;
            @SerializedName("__v")
            @Expose
            private int v;
            @SerializedName("repositories")
            @Expose
            private Repositories repositories;
            @SerializedName("contacts")
            @Expose
            private Contacts contacts;
            @SerializedName("profileValues")
            @Expose
            private ProfileValues profileValues;
            @SerializedName("publicInfo")
            @Expose
            private PublicInfo publicInfo;
            @SerializedName("specialization")
            @Expose
            private String specialization;
            @SerializedName("role")
            @Expose
            private String role;
            @SerializedName("updated")
            @Expose
            private String updated;

            public Repositories getRepositories() {
                return repositories;
            }

            public ProfileValues getProfileValues() {
                return profileValues;
            }

            public PublicInfo getPublicInfo() {
                return publicInfo;
            }

            public String getFullName() {
                return firstName + " " + secondName;
            }

            public String getId(){ return id;}
     /*   public ProfileValues getProfileValues(){
            return profileValues;
        }
        public Contacts getContacts() { return contacts; }
      //  public Repositories getRepositories() { return Repositories;}
        public PublicInfo getPublicInfo() {return publicInfo;}
        public String getFirstName() {return firstName; }
        public String getSecondName() {return secondName; }*/
        }

        public class Repositories{
            @SerializedName("repo")
            @Expose
            public List<Repo> repo = new ArrayList<Repo>();
            @SerializedName("updated")
            @Expose
            public String updated;

            public List<Repo> getRepo(){
                return repo;    }
        }

        public class Repo {
            @SerializedName("_id")
            @Expose
            public String id;
            @SerializedName("git")
            @Expose
            public String git;
            @SerializedName("title")
            @Expose
            public String title;

            public String getGit() {
                return git;
            }

            public String getId() { return id; }
        }

        public class PublicInfo {
            @SerializedName("bio")
            @Expose
            public String bio;
            @SerializedName("avatar")
            @Expose
            public String avatar;
            @SerializedName("photo")
            @Expose
            public String photo;
            @SerializedName("updated")
            @Expose
            public String updated;

            public String getBio() { return bio; }
            public String getAvatar() { return avatar;}
            public String getPhoto() { return photo;}

            public void setPhoto(String photo) {
                this.photo = photo;
            }
        }

        public class ProfileValues{
            @SerializedName("homeTask")
            @Expose
            public int homeTask;
            @SerializedName("projects")
            @Expose
            public int projects;
            @SerializedName("linesCode")
            @Expose
            public int linesCode;
            @SerializedName("rait")
            @Expose
            public int rait;
            @SerializedName("updated")
            @Expose
            public String updated;

            public int getProjects() {
                return projects;
            }

            public int getLinesCode() {
                return linesCode;
            }

            public int getRating() {
                return rait;
            }
        }

        public class Data{
            @SerializedName("user")
            @Expose
            public UserData user;
            @SerializedName("token")
            @Expose
            public String token;

            public String getToken(){
                return token;
            }

            public UserData getUser() {
                return user;
            }
        }

        public class Contacts{
            @SerializedName("vk")
            @Expose
            public String vk;
            @SerializedName("phone")
            @Expose
            public String phone;
            @SerializedName("email")
            @Expose
            public String email;
            @SerializedName("updated")
            @Expose
            public String updated;

            public String getVk() { return vk; }
            public String getPhone() { return phone; }
            public String getEmail() { return email; }
        }



    }