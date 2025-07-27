package com.hsdroid.bulkrepouploadforgithub.data

object Endpoints {
    const val repoCreation = "https://api.github.com/user/repos"
    const val githubToken =
        "https://github.com/settings/tokens/new?scopes=repo,workflow,user:follow" //to remove
    const val verifyGithubToken = "https://api.github.com/user"
}