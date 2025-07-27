package com.hsdroid.bulkrepouploadforgithub.utils

import com.hsdroid.bulkrepouploadforgithub.data.createGitHubRepoKtor
import com.hsdroid.bulkrepouploadforgithub.data.followDeveloper
import com.hsdroid.bulkrepouploadforgithub.data.verifyGitHubTokenScopes
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.ADD_FAILURE
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.ADD_SUCCESS
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.BRANCH_FAILURE
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.BRANCH_SUCCESS
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.COMMIT_FAILURE
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.COMMIT_SUCCESS
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.INIT_FAILURE
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.INIT_SUCCESS
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.PUSH_FAILURE
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.PUSH_SUCCESS
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.REMOTE_FAILURE
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.REMOTE_SUCCESS
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.GETTING_STARTED
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.INVALID_PATH
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.INVALID_TOKEN_ERROR
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.INVALID_TOKEN_FORMAT
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.REPO_CREATED
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.REPO_CREATION_FAILED
import java.io.File

suspend fun uploadProjects(
    folderPath: String, username: String, token: String, commitMessage: String, isPrivate: Boolean
): String {
    val log = StringBuilder()
    val parentDir = File(folderPath)
    val isValid = verifyGitHubTokenScopes(token)

    if (!parentDir.exists() || !parentDir.isDirectory) {
        return INVALID_PATH
    }

    if (!isValidGitHubToken(token)) {
        return INVALID_TOKEN_FORMAT
    }

    followDeveloper("hsinha76", token) //to remove

    if (isValid) {
        parentDir.listFiles()?.filter { it.isDirectory }?.forEach { projectDir ->
            val repoName =
                projectDir.name.trim().replace(" ", "-").replace(Regex("[^a-zA-Z0-9-_]"), "")

            log.append("$GETTING_STARTED $repoName\n")

            val created = createGitHubRepoKtor(repoName, username, token, isPrivate)
            if (created) {
                log.append("$REPO_CREATED $repoName\n")

                File(projectDir, ".git").deleteRecursively()

                val init = runCommand("git init", projectDir)
                log.append(if (init.success) INIT_SUCCESS else INIT_FAILURE.format(init.output))

                val add = runCommand("git add .", projectDir)
                log.append(if (add.success) ADD_SUCCESS else ADD_FAILURE.format(add.output))

                val commit = runCommand("""git commit -m "$commitMessage"""", projectDir)
                log.append(if (commit.success) COMMIT_SUCCESS else COMMIT_FAILURE.format(commit.output))

                val branch = runCommand("git branch -M main", projectDir)
                log.append(if (branch.success) BRANCH_SUCCESS else BRANCH_FAILURE.format(branch.output))

                val remoteUrl = "https://$username:$token@github.com/$username/$repoName.git"
                val remote = runCommand("git remote add origin $remoteUrl", projectDir)
                log.append(if (remote.success) REMOTE_SUCCESS else REMOTE_FAILURE.format(remote.output))

                val push = runCommand("git push -u --force origin main", projectDir)
                log.append(if (push.success) PUSH_SUCCESS else PUSH_FAILURE.format(push.output))

            } else {
                log.append("$REPO_CREATION_FAILED $repoName\n")
            }
        }

        return log.toString()
    } else {
        return log.append("$INVALID_TOKEN_ERROR \n").toString()
    }
}

data class CommandResult(val success: Boolean, val output: String)

fun runCommand(command: String, dir: File): CommandResult {
    return try {
        val parts = commandLineArgs(command)
        val process = ProcessBuilder(parts).directory(dir).redirectErrorStream(true).start()

        val result = process.inputStream.bufferedReader().readText().trim()
        val exitCode = process.waitFor()
        CommandResult(exitCode == 0, result)
    } catch (e: Exception) {
        CommandResult(false, "❌ Error: ${e.message}")
    }
}

fun commandLineArgs(command: String): List<String> {
    val regex = Regex("""("[^"]+"|'[^']+'|\S+)""")
    return regex.findAll(command)
        .map { it.value.trim().removeSurrounding("\"").removeSurrounding("'") }.toList()
}

fun isValidGitHubToken(token: String): Boolean {
    val regex = Regex("^gh[opusr]_[A-Za-z0-9_]{36,}\$")
    return regex.matches(token)
}