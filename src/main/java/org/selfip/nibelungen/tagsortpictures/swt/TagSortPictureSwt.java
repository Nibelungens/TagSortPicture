package org.selfip.nibelungen.tagsortpictures.swt;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.selfip.nibelungen.tagsortpictures.core.TagSortPicturesCore;
import org.selfip.nibelungen.tagsortpictures.core.interfaces.TemplateGuiInterface;
import org.selfip.nibelungen.tagsortpictures.job.MessageRowTSP;
import org.selfip.nibelungen.tagsortpictures.job.MessageRowTSPEnum;
import org.selfip.nibelungen.tagsortpictures.swt.interfaces.TagSortPicturesSwtInterface;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

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
	 * @wbp.parser.entryPoint
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		mainWindow.open();
		mainWindow.layout();
		while (!mainWindow.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private Shell CreateShell() {
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
	
	/**
	 * 
	 */
	private void createTabsPanel() {
		TabFolder tabFolder = new TabFolder(mainWindow, SWT.NONE);
		GridData gd_tabFolder = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 3);
		
		gd_tabFolder.widthHint = 536;

		tabFolder.setLayoutData(gd_tabFolder);
		
		this.createTabParams(tabFolder);
		this.createTabLog(tabFolder);
	}
	
	/**
	 * 
	 * @param tabFolder TabFolder
	 */
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
		
		textSource = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		textSource.setBackground(TagSortPictureSwt.RED);
		textSource.setEditable(false);
		textSource.setText(Messages.TagSortPictureSwt_textSourceFolder);
		textSource.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		btnSourceFolder = new Button(composite, SWT.NONE);
		btnSourceFolder.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnSourceFolder.setText(Messages.TagSortPictureSwt_btnSearchSourceFolder);
		
		Label lblNbFiles = new Label(composite, SWT.CENTER);
		GridData gd_lblNbFiles = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_lblNbFiles.widthHint = 125;
		lblNbFiles.setLayoutData(gd_lblNbFiles);
		lblNbFiles.setText(Messages.TagSortPictureSwt_lblNumberFiles);
		
		textNbFiles = new Text(composite, SWT.BORDER | SWT.READ_ONLY | SWT.CENTER);
		textNbFiles.setText(Messages.TagSortPictureSwt_textDefaultNoNumberFiles);
		GridData gd_textNbFiles = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_textNbFiles.widthHint = 74;
		textNbFiles.setLayoutData(gd_textNbFiles);
		
		btnRefreshNumber = new Button(composite, SWT.NONE);
		btnRefreshNumber.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnRefreshNumber.setText(Messages.TagSortPictureSwt_btnRefreshNumberFiles);
		
		textDestination = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		textDestination.setBackground(TagSortPictureSwt.RED);
		textDestination.setEditable(false);
		textDestination.setText(Messages.TagSortPictureSwt_textDestinationFolder);
		textDestination.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		btnDestinationFolder = new Button(composite, SWT.NONE);
		btnDestinationFolder.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnDestinationFolder.setText(Messages.TagSortPictureSwt_btnSearchDestinationFolder);
	}
	
	/**
	 * 
	 * @param tabFolder TabFolder
	 */
	private void createTabLog(TabFolder tabFolder) {
		TabItem tabLogs = new TabItem(tabFolder, SWT.NONE);
		tabLogs.setText(Messages.TagSortPictureSwt_tbtmLogs_text);
		
		listLogs = new List(tabFolder, SWT.H_SCROLL | SWT.V_SCROLL);
		tabLogs.setControl(listLogs);
	}
	
	/**
	 * 
	 */
	private void createTagsPanel() {
		textAddTag = new Text(mainWindow, SWT.BORDER);
		textAddTag.setText("");
		textAddTag.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		btnAddTag = new Button(mainWindow, SWT.NONE);
		GridData gd_btnAddTag = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_btnAddTag.widthHint = 75;
		btnAddTag.setLayoutData(gd_btnAddTag);
		btnAddTag.setText(Messages.TagSortPictureSwt_btnAddTag);
		
		btnDelTag = new Button(mainWindow, SWT.NONE);
		btnDelTag.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnDelTag.setText(Messages.TagSortPictureSwt_btnDelTag);
		
		listExcludeTag = new List(mainWindow, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		listExcludeTag.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
	}
	
	/**
	 * 
	 */
	private void createProgressbarPanel() {
		progressBar = new ProgressBar(mainWindow, SWT.NONE);
		GridData gd_progressBar = new GridData(SWT.LEFT, SWT.CENTER, true, false, 3, 1);
		gd_progressBar.widthHint = 705;
		progressBar.setLayoutData(gd_progressBar);
		new Label(mainWindow, SWT.NONE);
		new Label(mainWindow, SWT.NONE);
	}
	
	/**
	 * 
	 */
	private void createValidationPanel() {
		btnSort = new Button(mainWindow, SWT.NONE);
		btnSort.setEnabled(false);
		btnSort.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, true, 1, 1));
		btnSort.setText(Messages.TagSortPictureSwt_btnSort_text);
	}
	
	/**
	 * 
	 * @param textfield Text
	 * @param str String
	 */
	private void textFieldValid(Text textfield, String str) {
		textfield.setText(str);
		textfield.setBackground(TagSortPictureSwt.GREEN);
	}
	
	/**
	 * 
	 * @param textfield Text
	 * @param str String
	 */
	private void textFieldWarning(Text textfield, String str) {
		textfield.setText(str);
		textfield.setBackground(TagSortPictureSwt.ORANGE);
	}
	
	/**
	 * 
	 * @param text String
	 */
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
		textNbFiles.setText(tagSortPictures.getNbFiles().toString());;
	}
	
	/**
	 * 
	 */
	private void createListenerTabParamsPanel() {
		btnSourceFolder.addSelectionListener(new SelectionAdapter() {
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

		btnRefreshNumber.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if(textSource.getBackground().equals(TagSortPictureSwt.GREEN)) {
					setNumberOfFile(textSource.getText());
				}
			}
		});

		btnDestinationFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String pathDest = dialog.open();
				
				if(pathDest!=null) {
					textFieldValid(textSource, pathDest);	
				}				
			}
		});
	}
	
	/**
	 * 
	 */
	private void createListenerTagsPanel() {
		textAddTag.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.keyCode==16777296) {
					listExcludeTag.add(textAddTag.getText());
					textAddTag.setText("");
				}
			}
		});
		
		btnAddTag.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				listExcludeTag.add(textAddTag.getText());
			}
		});
		
		btnDelTag.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(listExcludeTag.getSelectionIndex()!=-1) listExcludeTag.remove(listExcludeTag.getSelectionIndex());
			}
		});
		
		listExcludeTag.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println(e.keyCode);
				
				if((e.keyCode==127 || e.keyCode==8) && listExcludeTag.getSelectionIndex()!=-1) {
					listExcludeTag.remove(listExcludeTag.getSelectionIndex());
				}
			}
		});
	}
	
	/**
	 * 
	 */
	private void createListenerValidationPanel() {
		btnSort.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TemplateGuiInterface templateGui = new TemplateGuiInterface() {
					
					@Override
					public void setProgressBarTotal(Integer hundredPercent) {
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
				if(!dest.exists()) dest.mkdir();
				
				String[] strTab = listExcludeTag.getItems();
				java.util.List<String> listeExclude = new ArrayList<String>();
				
				for (int ii = 0; ii < strTab.length; ii++) {
					listeExclude.add(strTab[ii]);					
				}
					
				TagSortPicturesCore tagSortPictures = new TagSortPicturesCore(textSource.getText(), textDestination.getText(), listeExclude, templateGui);
				tagSortPictures.run();
			}
		});
	}

	
	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		mainWindow = this.CreateShell();		
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
