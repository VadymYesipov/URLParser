package com.company;

/**
 * Created by User on 024 24.06.17.
 */
public class Parser {

    private String[] parts;

    public Parser() {
        parts = new String[8];
    }

    public String[] parse(String URL) {
        if (URL.contains(":")) {
            String[] firstArray = URL.split(":", 2);
            if (firstArray.length > 1) {
                parts[0] = firstArray[0];
                if (firstArray[1].contains("//")) {
                    parseAll(firstArray[1], firstArray);
                } else if (firstArray[1].contains("/")) {
                    parsePath(firstArray[1]);
                } else if (firstArray[1].contains("?")) {
                    parseQuery(firstArray[1]);
                } else if (firstArray[1].contains("#")) {
                    parseFragment(firstArray[1]);
                } else {
                    return parts;
                }
            } else {
                parts[0] = firstArray[0];
            }
            return parts;
        } else {
            return parts;
        }
    }

    private void method(String string, boolean bool){
        if (string.contains("/")) {
            if(bool) {
                parts[3] = string.substring(0, string.indexOf('/'));
            }
            parsePath(string.substring(string.indexOf('/')));
        } else if (string.contains("?")) {
            if(bool) {
                parts[3] = string.substring(0, string.indexOf('?'));
            }
            parseQuery(string.substring(string.indexOf('?')));
        } else if (string.contains("#")) {
            if(bool) {
                parts[3] = string.substring(0, string.indexOf('#'));
            }
            parseFragment(string.substring(string.indexOf('#')));
        }
    }

    private void parseAll(String hostname, String[] firstArray) {
        StringBuilder stringBuilder = new StringBuilder(hostname);
        stringBuilder.delete(0, 2);
        hostname = new String(stringBuilder);
        if (hostname.contains("@")) {
            String[] secondArray = hostname.split("@");
            parseUserAndPassword(secondArray);
            parseHostAndPort(secondArray);
            method(hostname, false);
        } else {
            if (hostname.contains(":")) {
                String[] secondArray = hostname.split(":");
                parts[3] = secondArray[0];
                parts[4] = secondArray[1];
                if(secondArray[1].contains("/")) {
                    parts[4] = secondArray[1].substring(0, secondArray[1].indexOf('/'));
                }else if(secondArray[1].contains("?")){
                    parts[4] = secondArray[1].substring(0, secondArray[1].indexOf('?'));
                }else if(secondArray[1].contains("#")){
                    parts[4] = secondArray[1].substring(0, secondArray[1].indexOf('#'));
                }
                method(hostname, false);
            } else{
                method(hostname, true);
            }
        }
    }

    private void parseUserAndPassword(String[] secondArray) {
        if (secondArray[0].contains(":")) {
            String[] thirdArray = secondArray[0].split(":");
            parts[1] = thirdArray[0];
            parts[2] = thirdArray[1];
        } else {
            parts[1] = secondArray[0];
        }
    }

    private void parseHostAndPort(String[] secondArray) {
        if (secondArray[1].contains(":")) {
            String[] thirdArray = secondArray[1].split(":");
            parts[3] = thirdArray[0];
            if(thirdArray[1].contains("/")) {
                parts[4] = thirdArray[1].substring(0, thirdArray[1].indexOf('/'));
            }else if(thirdArray[1].contains("?")){
                parts[4] = thirdArray[1].substring(0, thirdArray[1].indexOf('?'));
            }else if(thirdArray[1].contains("#")){
                parts[4] = thirdArray[1].substring(0, thirdArray[1].indexOf('#'));
            }
        } else {
            parts[3] = secondArray[1];
            if(secondArray[1].contains("/")) {
                parts[3] = secondArray[1].substring(0, secondArray[1].indexOf('/'));
            }else if(secondArray[1].contains("?")){
                parts[3] = secondArray[1].substring(0, secondArray[1].indexOf('?'));
            }else if(secondArray[1].contains("#")){
                parts[3] = secondArray[1].substring(0, secondArray[1].indexOf('#'));
            }
        }
    }

    private void parsePath(String path) {
        StringBuilder stringBuilder = new StringBuilder(path);
        stringBuilder.delete(0, 1);
        path = new String(stringBuilder);
        if (path.contains("?") && path.contains("#")) {
            String[] secondArray = path.split("\\?");
            parts[5] = secondArray[0];
            String[] thirdArray = secondArray[1].split("#");
            parts[6] = thirdArray[0];
            parts[7] = thirdArray[1];
        } else if (path.contains("?")) {
            String[] secondArray = path.split("\\?");
            parts[5] = secondArray[0];
            parts[6] = secondArray[1];
        } else if (path.contains("#")) {
            String[] secondArray = path.split("#");
            parts[5] = secondArray[0];
            parts[7] = secondArray[1];
        } else {
            parts[5] = path;
        }
    }

    private void parseQuery(String query) {
        StringBuilder stringBuilder = new StringBuilder(query);
        stringBuilder.delete(0, 1);
        query = new String(stringBuilder);
        if (query.contains("#")) {
            String[] secondArray = query.split("#");
            parts[6] = secondArray[0];
            parts[7] = secondArray[1];
        } else {
            parts[6] = query;
        }
    }

    private void parseFragment(String fragment) {
        StringBuilder stringBuilder = new StringBuilder(fragment);
        stringBuilder.delete(0, 1);
        fragment = new String(stringBuilder);
        parts[7] = fragment;
    }
}
