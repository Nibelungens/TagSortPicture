package org.nibelungen.tagsortpictures.swt;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.nibelungen.tagsortpictures.core.interfaces.TemplateGuiInterface;
import org.nibelungen.tagsortpictures.core.TagSortPicturesCore;
import org.nibelungen.tagsortpictures.job.MessageRowTSP;
import org.nibelungen.tagsortpictures.job.MessageRowTSPEnum;
import org.nibelungen.tagsortpictures.swt.interfaces.TagSortPicturesSwtInterface;

public class TagSortPictureSwt implements TagSortPicturesSwtInterface {

	protected Shell mainWindow;
	private Text textSource;
	private Text textDestination;
	private Text textNbFiles;
	private Text textAddTag;
	private DirectoryDialog dialog;
	private Button btnSourceFolder;
	private Button btnRefreshNumber;
	private Button btnDestinationFolder;
	private Button btnAddTag;
	private Button btnDelTag;
	private Button btnSort;
	private List listExcludeTag;
	private ProgressBar progressBar;
	private List listLogs;

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		this.mainWindow.open();
		this.mainWindow.layout();
		while (!this.mainWindow.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private Shell createShell() {
		Shell mainWindow = new Shell(SWT.CLOSE | SWT.MIN);
		GridLayout gl_mainWindow = new GridLayout(3, false);
		Canvas canvas = new Canvas(mainWindow, SWT.NONE);
		
		gl_mainWindow.verticalSpacing = 10;
		canvas.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		
		mainWindow.setMinimumSize(new Point(720, 360));
		mainWindow.setImage(SWTResourceManager.getImage(TagSortPictureSwt.class, TagSortPictureSwt.ICO_TITLE));
		mainWindow.setSize(720, 275);
		mainWindow.setText(Messages.TagSortPictureSwt_shlTagsortpictures_text);
		mainWindow.setLayout(gl_mainWindow);

		return mainWindow;
	}

	private void createTabsPanel() {
		TabFolder tabFolder = new TabFolder(mainWindow, SWT.NONE);
		GridData gd_tabFolder = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 3);
		
		gd_tabFolder.widthHint = 536;

		tabFolder.setLayoutData(gd_tabFolder);
		
		this.createTabParams(tabFolder);
		this.createTabLog(tabFolder);
	}
	
	private void createTabParams(TabFolder tabFolder) {
		TabItem tabParameters = new TabItem(tabFolder, SWT.NONE);
		tabParameters.setText(Messages.TagSortPictureSwt_tbtmParams_text);
		
		Composite composite = new Composite(tabFolder, SWT.NONE);
		tabParameters.setControl(composite);
		GridLayout gl_composite = new GridLayout(3, false);
		gl_composite.verticalSpacing = 15;
		gl_composite.marginBottom = 15;
		gl_composite.marginTop = 15;
		gl_composite.horizontalSpacing = 10;
		composite.setLayout(gl_composite);
		
		this.textSource = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		this.textSource.setBackground(TagSortPictureSwt.RED);
		this.textSource.setEditable(false);
		this.textSource.setText(Messages.TagSortPictureSwt_textSourceFolder);
		this.textSource.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		this.btnSourceFolder = new Button(composite, SWT.NONE);
		this.btnSourceFolder.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		this.btnSourceFolder.setText(Messages.TagSortPictureSwt_btnSearchSourceFolder);
		
		Label lblNbFiles = new Label(composite, SWT.CENTER);
		GridData gd_lblNbFiles = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_lblNbFiles.widthHint = 125;
		lblNbFiles.setLayoutData(gd_lblNbFiles);
		lblNbFiles.setText(Messages.TagSortPictureSwt_lblNumberFiles);
		
		this.textNbFiles = new Text(composite, SWT.BORDER | SWT.READ_ONLY | SWT.CENTER);
		this.textNbFiles.setText(Messages.TagSortPictureSwt_textDefaultNoNumberFiles);
		GridData gd_textNbFiles = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_textNbFiles.widthHint = 74;
		this.textNbFiles.setLayoutData(gd_textNbFiles);
		
		this.btnRefreshNumber = new Button(composite, SWT.NONE);
		this.btnRefreshNumber.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		this.btnRefreshNumber.setText(Messages.TagSortPictureSwt_btnRefreshNumberFiles);
		
		this.textDestination = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		this.textDestination.setBackground(TagSortPictureSwt.RED);
		this.textDestination.setEditable(false);
		this.textDestination.setText(Messages.TagSortPictureSwt_textDestinationFolder);
		this.textDestination.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		this.btnDestinationFolder = new Button(composite, SWT.NONE);
		this.btnDestinationFolder.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		this.btnDestinationFolder.setText(Messages.TagSortPictureSwt_btnSearchDestinationFolder);
	}

	private void createTabLog(TabFolder tabFolder) {
		TabItem tabLogs = new TabItem(tabFolder, SWT.NONE);
		tabLogs.setText(Messages.TagSortPictureSwt_tbtmLogs_text);
		
		this.listLogs = new List(tabFolder, SWT.H_SCROLL | SWT.V_SCROLL);
		tabLogs.setControl(this.listLogs);
	}

	private void createTagsPanel() {
		this.textAddTag = new Text(mainWindow, SWT.BORDER);
		this.textAddTag.setText("");
		this.textAddTag.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		this.btnAddTag = new Button(mainWindow, SWT.NONE);
		GridData gd_btnAddTag = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_btnAddTag.widthHint = 75;
		this.btnAddTag.setLayoutData(gd_btnAddTag);
		this.btnAddTag.setText(Messages.TagSortPictureSwt_btnAddTag);
		
		this.btnDelTag = new Button(mainWindow, SWT.NONE);
		this.btnDelTag.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		this.btnDelTag.setText(Messages.TagSortPictureSwt_btnDelTag);
		
		listExcludeTag = new List(mainWindow, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		listExcludeTag.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
	}

	private void createProgressbarPanel() {
		this.progressBar = new ProgressBar(this.mainWindow, SWT.NONE);
		GridData gd_progressBar = new GridData(SWT.LEFT, SWT.CENTER, true, false, 3, 1);
		gd_progressBar.widthHint = 705;
		this.progressBar.setLayoutData(gd_progressBar);
		new Label(this.mainWindow, SWT.NONE);
		new Label(this.mainWindow, SWT.NONE);
	}

	private void createValidationPanel() {
		this.btnSort = new Button(this.mainWindow, SWT.NONE);
		this.btnSort.setEnabled(false);
		this.btnSort.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, true, 1, 1));
		this.btnSort.setText(Messages.TagSortPictureSwt_btnSort_text);
	}

	private void textFieldValid(Text textField, String str) {
		textField.setText(str);
		textField.setBackground(TagSortPictureSwt.GREEN);
	}

	private void textFieldWarning(Text textField, String str) {
		textField.setText(str);
		textField.setBackground(TagSortPictureSwt.ORANGE);
	}

	private void setNumberOfFile(String text) {
		TemplateGuiInterface templateGui = new TemplateGuiInterface() {
			
			@Override
			public void setProgressBarTotal(Integer hundredPercent) {}
			
			@Override
			public void addMessage(MessageRowTSP msg) {
				listLogs.add(msg.getErrorMessage());
			}

			@Override
			public void addIncrementProgressBar() {}
		};
			
		TagSortPicturesCore tagSortPictures = new TagSortPicturesCore(text, null, null, templateGui);
		this.textNbFiles.setText(tagSortPictures.getNbFiles().toString());
	}

	private void createListenerTabParamsPanel() {
		this.btnSourceFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String path = dialog.open();
				
				if(path!=null) {
					textFieldValid(textSource, path);
					textFieldWarning(textDestination, path + "\\" + TagSortPictureSwt.FOLDER_DEST_DEFAULT);
					setNumberOfFile(textSource.getText());
					
					btnSort.setEnabled(true);
				}
			}
		});

		this.btnRefreshNumber.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if(textSource.getBackground().equals(TagSortPictureSwt.GREEN)) {
					setNumberOfFile(textSource.getText());
				}
			}
		});

		this.btnDestinationFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String pathDest = dialog.open();
				
				if(pathDest!=null) {
					textFieldValid(textSource, pathDest);	
				}				
			}
		});
	}

	private void createListenerTagsPanel() {
		this.textAddTag.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.keyCode==16777296) {
					listExcludeTag.add(textAddTag.getText());
					textAddTag.setText("");
				}
			}
		});
		
		this.btnAddTag.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				listExcludeTag.add(textAddTag.getText());
			}
		});
		
		this.btnDelTag.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(listExcludeTag.getSelectionIndex()!=-1) listExcludeTag.remove(listExcludeTag.getSelectionIndex());
			}
		});
		
		this.listExcludeTag.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println(e.keyCode);
				
				if((e.keyCode==127 || e.keyCode==8) && listExcludeTag.getSelectionIndex()!=-1) {
					listExcludeTag.remove(listExcludeTag.getSelectionIndex());
				}
			}
		});
	}

	private void createListenerValidationPanel() {
		this.btnSort.addSelectionListener(this.getSelectionAdapter());
	}

	SelectionAdapter getSelectionAdapter() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TemplateGuiInterface templateGui = new TemplateGuiInterface() {

					@Override
					public void setProgressBarTotal(Integer hundredPercent) {
						progressBar.setSelection(0);
						progressBar.setMaximum(hundredPercent);
					}

					@Override
					public void addMessage(MessageRowTSP msg) {
						if(!msg.getErrorType().equals(MessageRowTSPEnum.DEBUG)) listLogs.add(msg.getErrorMessage());

					}

					@Override
					public void addIncrementProgressBar() {
						progressBar.setSelection(progressBar.getSelection()+1);
					}
				};

				File dest = new File(textDestination.getText());
				boolean isDest = dest.exists();

				if(!isDest) {
					isDest = dest.mkdir();
				}

				if (isDest) {
					String[] strTab = listExcludeTag.getItems();
					java.util.List<String> listExclude = new ArrayList<>();

					Collections.addAll(listExclude, strTab);

					TagSortPicturesCore tagSortPictures = new TagSortPicturesCore(textSource.getText(), textDestination.getText(), listExclude, templateGui);
					tagSortPictures.run();
				}
			}
		};
	}

	protected void createContents() {
		this.mainWindow = this.createShell();		
		this.dialog = new DirectoryDialog(mainWindow);
		this.createTabsPanel();
		this.createTagsPanel();
		this.createProgressbarPanel();
		this.createValidationPanel();
		
		this.createListenerTabParamsPanel();
		this.createListenerTagsPanel();
		this.createListenerValidationPanel();
	}
}
