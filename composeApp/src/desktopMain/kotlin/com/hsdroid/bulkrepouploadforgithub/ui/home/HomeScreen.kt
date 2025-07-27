package com.hsdroid.bulkrepouploadforgithub.ui.home

import CustomBlue
import CustomWhite
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hsdroid.bulkrepouploadforgithub.data.Endpoints.githubToken
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.ACCESS_TOKEN_TXT
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.ACTIVITY_LOG
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.ADVANCE_SETTINGS
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.APP_NAME
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.BROWSE_PROJECT
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.COMMIT_MESSAGE
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.GET_TOKEN
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.GITHUB_TOKEN_PAGE
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.GITHUB_USERNAME
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.HIDE_SETTINGS
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.INVALID_FOLDER_WARNING
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.NO_ACTIVITY
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.PARENT_FOLDER_WARNING
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.PASTE
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.PRIVATE_REPOSITORY
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.PUBLIC_REPOSITORY
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.SELECT_FOLDER
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.SELECTED_TXT
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.TUTORIAL_1
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.TUTORIAL_2
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.TUTORIAL_3
import com.hsdroid.bulkrepouploadforgithub.ui.common.Strings.UPLOAD_PROJECTS_TXT
import com.hsdroid.bulkrepouploadforgithub.ui.common.showInfoDialog
import com.hsdroid.bulkrepouploadforgithub.utils.uploadProjects
import githubbulkupload.composeapp.generated.resources.Res
import githubbulkupload.composeapp.generated.resources.clipboard
import githubbulkupload.composeapp.generated.resources.folder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.painterResource
import java.awt.Desktop
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.net.URI
import javax.swing.JFileChooser
import javax.swing.JOptionPane

@Composable
fun HomeScreen() {
    var username by remember { mutableStateOf("") }
    var token by remember { mutableStateOf("") }
    var folderPath by remember { mutableStateOf("") }
    var logOutput by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var isExpanded by remember { mutableStateOf(false) }
    var commitMessageValue by remember { mutableStateOf("Initial commit") }
    var isPrivate by remember { mutableStateOf(true) }
    val scrollState = rememberScrollState()

    LaunchedEffect(logOutput) {
        scrollState.animateScrollTo(scrollState.maxValue)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = androidx.compose.ui.graphics.Color.White)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            APP_NAME,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(2.dp),
            colors = CardDefaults.cardColors(
                containerColor = CustomWhite
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text(GITHUB_USERNAME, fontSize = 14.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 14.sp),
                )

                OutlinedTextField(
                    value = token,
                    onValueChange = { token = it },
                    label = { Text(ACCESS_TOKEN_TXT, fontSize = 14.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    trailingIcon = {
                        Row(
                            modifier = Modifier.wrapContentWidth().padding(horizontal = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TokenHelpButton()
                            Spacer(modifier = Modifier.width(4.dp))
                            PasteButton(onPasteClick = {
                                token = it
                            })
                        }
                    },
                    textStyle = TextStyle(fontSize = 14.sp)
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .wrapContentWidth()
                            .clickable {

                                showInfoDialog(Strings.FOLDER_STRUCTURE_WARNING.trimIndent())

                                val chooser = JFileChooser()
                                chooser.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
                                chooser.dialogTitle = BROWSE_PROJECT

                                val result = chooser.showOpenDialog(null)
                                if (result == JFileChooser.APPROVE_OPTION) {
                                    val selectedFolder = chooser.selectedFile
                                    val hasSubFolders =
                                        selectedFolder.listFiles()?.any { it.isDirectory } == true

                                    if (selectedFolder.isDirectory && hasSubFolders) {
                                        folderPath = selectedFolder.absolutePath
                                    } else {
                                        JOptionPane.showMessageDialog(
                                            null,
                                            PARENT_FOLDER_WARNING,
                                            INVALID_FOLDER_WARNING,
                                            JOptionPane.ERROR_MESSAGE
                                        )
                                    }
                                }

                            },
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .wrapContentWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Image(
                                painter = painterResource(Res.drawable.folder),
                                contentDescription = SELECT_FOLDER,
                                modifier = Modifier.size(18.dp)
                            )

                            val folderName =
                                folderPath.split("/").lastOrNull()?.takeIf { it.isNotBlank() }
                                    ?: BROWSE_PROJECT
                            val isSelected = folderPath.isNotEmpty()

                            Text(
                                text = if (isSelected) "$SELECTED_TXT $folderName" else folderName,
                                style = if (isSelected)
                                    MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
                                else
                                    MaterialTheme.typography.bodySmall,
                                color = if (isSelected)
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.onSurface,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }

                Text(
                    text = if (isExpanded) HIDE_SETTINGS else ADVANCE_SETTINGS,
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable {
                            if (!isLoading) {
                                isExpanded = !isExpanded
                            }
                        },
                    fontSize = 11.sp
                )

                if (isExpanded) {
                    OutlinedTextField(
                        value = commitMessageValue,
                        onValueChange = { commitMessageValue = it },
                        label = { Text(COMMIT_MESSAGE, fontSize = 14.sp) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        textStyle = TextStyle(fontSize = 14.sp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Switch(
                            checked = isPrivate,
                            onCheckedChange = { isPrivate = it },
                            modifier = Modifier
                                .scale(0.7f)
                                .padding(horizontal = 0.dp),
                            colors = SwitchDefaults.colors(
                                uncheckedThumbColor = MaterialTheme.colorScheme.outline,
                                uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        )

                        Text(
                            text = if (isPrivate) PRIVATE_REPOSITORY else PUBLIC_REPOSITORY,
                            style = MaterialTheme.typography.labelMedium,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }
        }

        Button(
            onClick = {
                isLoading = true
                isExpanded = false
                CoroutineScope(Dispatchers.IO).launch {
                    val log =
                        uploadProjects(folderPath, username, token, commitMessageValue, isPrivate)
                    withContext(Dispatchers.Main) {
                        logOutput = log
                        isLoading = false
                    }
                }
            },
            modifier = Modifier.wrapContentSize().align(Alignment.CenterHorizontally),
            enabled = username.isNotBlank() &&
                    token.isNotBlank() &&
                    folderPath.isNotBlank() &&
                    !isLoading && commitMessageValue.isNotBlank(),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(containerColor = CustomBlue)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
            } else {
                Text(UPLOAD_PROJECTS_TXT, fontSize = 12.sp)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                text = ACTIVITY_LOG,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxSize(),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(2.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CustomWhite
                )
            ) {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .verticalScroll(scrollState)
                ) {
                    Text(
                        text = if (logOutput.isBlank()) NO_ACTIVITY else logOutput,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        lineHeight = 20.sp
                    )
                }
            }
        }

    }
}

@Composable
fun PasteButton(onPasteClick: (String) -> Unit) {
    IconButton(onClick = {
        try {
            val clipboard = Toolkit.getDefaultToolkit().systemClipboard
            val pastedText =
                clipboard.getData(DataFlavor.stringFlavor) as? String
            if (!pastedText.isNullOrBlank()) {
                onPasteClick(pastedText)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }) {
        Image(
            modifier = Modifier.size(22.dp),
            painter = painterResource(Res.drawable.clipboard),
            contentDescription = PASTE
        )
    }
}

@Composable
fun TokenHelpButton() {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Text(
            modifier = Modifier.clickable {
                expanded = true
            },
            text = GET_TOKEN, style = MaterialTheme.typography.labelMedium,
            color = CustomBlue
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(color = androidx.compose.ui.graphics.Color.White)
                .widthIn(min = 300.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        expanded = false
                        try {
                            Desktop.getDesktop()
                                .browse(URI(githubToken))
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(GITHUB_TOKEN_PAGE)
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    TUTORIAL_1,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    TUTORIAL_2,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    TUTORIAL_3,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}