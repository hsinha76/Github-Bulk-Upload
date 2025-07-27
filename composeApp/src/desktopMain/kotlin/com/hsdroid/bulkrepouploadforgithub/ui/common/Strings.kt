package com.hsdroid.bulkrepouploadforgithub.ui.common

object Strings {
    const val APP_NAME = "Bulk Repo Uploader"
    const val SUB_TITLE = "Upload multiple GitHub repositories at once 🚀"
    const val CONTINUE_TXT = "Continue"
    const val GITHUB_USERNAME = "GitHub Username"
    const val ACCESS_TOKEN_TXT = "Personal Access Token"
    const val FOLDER_STRUCTURE_WARNING = """
        🚨Folder Structure Required 🚨
        
        Please select a parent folder that contains multiple child folders.
        Each child folder will be uploaded as a separate GitHub repository.
        
        Example:
        🗂 MyProjects/
           ├ 🗂 App1/
           ├ 🗂 App2/
           └ 🗂 App3/
        
        Each folder (App1, App2, etc.) should be a valid Git project folder.
    """
    const val PARENT_FOLDER_WARNING =
        "Please select a parent folder that contains at least one project subfolder."
    const val INVALID_FOLDER_WARNING = "Invalid Folder Structure"
    const val SELECT_FOLDER = "Select a folder"
    const val BROWSE_PROJECT = "Select Project Folder"
    const val SELECTED_TXT = "Selected:"
    const val UPLOAD_PROJECTS_TXT = "Upload Projects"
    const val ACTIVITY_LOG = "📝 Activity Log"
    const val NO_ACTIVITY = "No activity yet."
    const val PASTE = "Paste"
    const val GET_TOKEN = "Get Token"
    const val GITHUB_TOKEN_PAGE = "🔓 Open GitHub Token Page"
    const val TUTORIAL_1 = "Then follow these steps:"
    const val TUTORIAL_2 = "⏳ 1. Choose an expiration time and click 'Generate token'"
    const val TUTORIAL_3 = "📋 2. Copy the token and paste here"
    const val IMPORTANT = "Important"
    const val PRIVATE_REPOSITORY = "Private Repository"
    const val PUBLIC_REPOSITORY = "Public Repository"
    const val HIDE_SETTINGS = "Hide settings"
    const val ADVANCE_SETTINGS = "Advance settings"
    const val COMMIT_MESSAGE = "Commit message"

    const val INVALID_PATH = "Invalid folder path"
    const val INVALID_TOKEN_FORMAT = "Invalid token format"
    const val INVALID_TOKEN_ERROR =
        "Please generate a token with scopes: repo, workflow, user:follow."
    const val GETTING_STARTED = "⚡️Getting started"

    const val REPO_CREATED = "✅ Repo created:"
    const val REPO_CREATION_FAILED = "❌ Repo creation failed:"
    const val INIT_SUCCESS = "✅ Initialized Git repo\n"
    const val INIT_FAILURE = "❌ Failed to init Git: %s\n"
    const val ADD_SUCCESS = "✅ Staged project files\n"
    const val ADD_FAILURE = "❌ Failed to stage files: %s\n"
    const val COMMIT_SUCCESS = "✅ Files committed\n"
    const val COMMIT_FAILURE = "❌ Commit failed: %s\n"
    const val BRANCH_SUCCESS = "✅ Set branch to 'main'\n"
    const val BRANCH_FAILURE = "❌ Failed to set branch: %s\n"
    const val REMOTE_SUCCESS = "✅ Remote origin set\n"
    const val REMOTE_FAILURE = "❌ Failed to set remote: %s\n"
    const val PUSH_SUCCESS = "🚀 Code pushed to GitHub\n"
    const val PUSH_FAILURE = "❌ Push failed: %s\n"
}