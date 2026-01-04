import React, { useState, useEffect } from 'react';
import { Download, CheckSquare, Table, BarChart3, Database, Move, MousePointerClick, Clock, Search } from 'lucide-react';

export default function PlaywrightTestSite() {
  const [selectedTab, setSelectedTab] = useState('basics');

  // --- STATE: BASICS FORM ---
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    country: '',
    gender: '',
    skills: [],
    dateOfBirth: ''
  });
  const [errors, setErrors] = useState({});
  const [uploadedFile, setUploadedFile] = useState(null);
  const [alertMessage, setAlertMessage] = useState('');
  const [alertType, setAlertType] = useState('success');

  // --- STATE: ADVANCED DROPDOWN (Search/Select) ---
  const [customDropdownOpen, setCustomDropdownOpen] = useState(false);
  const [selectedCustomOption, setSelectedCustomOption] = useState('Select Framework');
  const [dropdownSearch, setDropdownSearch] = useState('');
  const frameworkOptions = ['Playwright', 'Selenium', 'Cypress', 'TestCafe', 'Puppeteer', 'WebdriverIO'];

  // --- STATE: TABLE (Pagination & Sorting) ---
  // Generating more data for pagination testing
  const initialTableData = Array.from({ length: 25 }, (_, i) => ({
    id: i + 1,
    name: `Employee ${i + 1}`,
    role: ['Developer', 'Tester', 'Manager', 'Designer'][i % 4],
    salary: 50000 + (i * 1000),
    status: i % 3 === 0 ? 'Inactive' : 'Active'
  }));
  
  const [tableData, setTableData] = useState(initialTableData);
  const [currentPage, setCurrentPage] = useState(1);
  const rowsPerPage = 5;
  const [sortConfig, setSortConfig] = useState({ key: null, direction: 'asc' });

  // --- STATE: DRAG AND DROP ---
  const [dragItems, setDragItems] = useState([
    { id: 'item-1', content: 'Test Case 1', list: 'todo' },
    { id: 'item-2', content: 'Test Case 2', list: 'todo' },
    { id: 'item-3', content: 'Test Case 3', list: 'done' },
  ]);
  const [dragOverItem, setDragOverItem] = useState(null);

  // --- STATE: ASYNC / WAIT FOR ---
  const [isLoading, setIsLoading] = useState(false);
  const [asyncData, setAsyncData] = useState(null);

  // --- STATE: HOVER ---
  const [isHovered, setIsHovered] = useState(false);

  // ================= HANDLERS =================

  // 1. FORM LOGIC
  const validateForm = () => {
    const newErrors = {};
    if (!formData.username.trim()) newErrors.username = 'Username is required';
    
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = () => {
    if (validateForm()) {
      setAlertMessage('Form submitted successfully!');
      setAlertType('success');
    } else {
      setAlertMessage('Validation failed!');
      setAlertType('error');
    }
  };

  const handleInputChange = (field, value) => {
    setFormData(prev => ({ ...prev, [field]: value }));
    if (errors[field]) setErrors(prev => ({ ...prev, [field]: undefined }));
  };

  const handleCheckboxChange = (skill) => {
    const newSkills = formData.skills.includes(skill)
      ? formData.skills.filter(s => s !== skill)
      : [...formData.skills, skill];
    setFormData(prev => ({ ...prev, skills: newSkills }));
  };

  // 2. FILE UPLOAD
  const handleFileUpload = (e) => {
    const file = e.target.files[0];
    if (file) setUploadedFile(file.name);
  };

  const handleDownload = () => {
    const blob = new Blob(['{"test": "data", "id": 123}'], { type: 'application/json' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = 'test-data.json';
    a.click();
    URL.revokeObjectURL(url);
  };

  // 3. TABLE LOGIC (Pagination + Sort + Delete)
  const handleSort = (key) => {
    let direction = 'asc';
    if (sortConfig.key === key && sortConfig.direction === 'asc') direction = 'desc';
    setSortConfig({ key, direction });

    const sorted = [...tableData].sort((a, b) => {
      if (a[key] < b[key]) return direction === 'asc' ? -1 : 1;
      if (a[key] > b[key]) return direction === 'asc' ? 1 : -1;
      return 0;
    });
    setTableData(sorted);
  };

  const deleteRow = (id) => {
    setTableData(tableData.filter(row => row.id !== id));
  };

  const indexOfLastRow = currentPage * rowsPerPage;
  const indexOfFirstRow = indexOfLastRow - rowsPerPage;
  const currentRows = tableData.slice(indexOfFirstRow, indexOfLastRow);
  const totalPages = Math.ceil(tableData.length / rowsPerPage);

  // 4. DRAG AND DROP HANDLERS (Native API)
  const onDragStart = (e, id) => {
    e.dataTransfer.setData("id", id);
  };

  const onDragOver = (e) => {
    e.preventDefault();
  };

  const onDrop = (e, list) => {
    const id = e.dataTransfer.getData("id");
    const updatedItems = dragItems.map(item => {
      if (item.id === id) return { ...item, list: list };
      return item;
    });
    setDragItems(updatedItems);
  };

  // 5. ASYNC LOGIC
  const fetchAsyncData = () => {
    setIsLoading(true);
    setAsyncData(null);
    // Simulating a 3-second network delay
    setTimeout(() => {
      setAsyncData({ message: 'Data loaded successfully after 3 seconds!' });
      setIsLoading(false);
    }, 3000);
  };

  // 6. ALERTS
  const showConfirm = () => {
    // eslint-disable-next-line no-restricted-globals
    if (confirm('Do you want to proceed?')) {
      setAlertMessage('Confirmed!');
    } else {
      setAlertMessage('Cancelled!');
    }
  };

  const showPrompt = () => {
    // eslint-disable-next-line no-restricted-globals
    const name = prompt('Please enter your name:');
    if (name) setAlertMessage(`Hello, ${name}!`);
  };

  const tabs = [
    { id: 'basics', label: 'Form & Inputs', icon: CheckSquare },
    { id: 'interactions', label: 'Drag & Drop / Hover', icon: MousePointerClick },
    { id: 'tables', label: 'Tables & Pagination', icon: Table },
    { id: 'async', label: 'Wait Conditions', icon: Clock },
    { id: 'advanced', label: 'Popups & Iframes', icon: BarChart3 },
  ];

  return (
    <div className="min-h-screen bg-gray-50 p-8 font-sans">
      <div className="max-w-6xl mx-auto">
        <header className="mb-8">
          <h1 className="text-3xl font-bold text-indigo-900">Playwright Java Practice Site</h1>
          <p className="text-gray-600">Designed to cover all Checklist items including Waits, Drag&Drop, and Pagination.</p>
        </header>

        {/* --- NAVIGATION --- */}
        <div className="flex gap-2 mb-6 flex-wrap">
          {tabs.map(tab => {
            const Icon = tab.icon;
            return (
              <button
                key={tab.id}
                onClick={() => setSelectedTab(tab.id)}
                className={`flex items-center gap-2 px-4 py-2 rounded-lg font-medium transition-all ${
                  selectedTab === tab.id ? 'bg-indigo-600 text-white shadow' : 'bg-white text-gray-700 hover:bg-gray-200'
                }`}
                data-testid={`nav-${tab.id}`}
              >
                <Icon size={18} /> {tab.label}
              </button>
            );
          })}
        </div>

        {/* ================= TAB: BASICS ================= */}
        {selectedTab === 'basics' && (
          <div className="bg-white rounded-xl shadow p-8 space-y-6">
            <h2 className="text-xl font-bold mb-4">Input Fields & Validations</h2>
            
            {/* Standard Inputs */}
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div>
                <label className="block text-sm font-medium mb-1">Username</label>
                <input
                  type="text"
                  data-testid="username-input"
                  value={formData.username}
                  onChange={(e) => handleInputChange('username', e.target.value)}
                  className="w-full border p-2 rounded"
                />
                {errors.username && <span className="text-red-500 text-sm">{errors.username}</span>}
              </div>
              
              {/* Native Dropdown */}
              <div>
                <label className="block text-sm font-medium mb-1">Country (Native Select)</label>
                <select 
                  data-testid="country-select"
                  className="w-full border p-2 rounded"
                  value={formData.country}
                  onChange={(e) => handleInputChange('country', e.target.value)}
                >
                  <option value="">Choose...</option>
                  <option value="us">USA</option>
                  <option value="vn">Vietnam</option>
                  <option value="jp">Japan</option>
                </select>
              </div>
            </div>

            {/* Checklist Item: Dropdowns built with divs/spans, search/select */}
            <div className="relative w-full md:w-1/2">
              <label className="block text-sm font-medium mb-1">Advanced Searchable Dropdown (Div-based)</label>
              <button
                data-testid="custom-dropdown-btn"
                onClick={() => setCustomDropdownOpen(!customDropdownOpen)}
                className="w-full border p-2 rounded text-left flex justify-between items-center bg-gray-50"
              >
                {selectedCustomOption} <Search size={16} />
              </button>
              
              {customDropdownOpen && (
                <div data-testid="custom-dropdown-menu" className="absolute z-10 w-full mt-1 bg-white border shadow-lg rounded max-h-48 overflow-auto">
                  <input
                    type="text"
                    placeholder="Search framework..."
                    data-testid="dropdown-search"
                    className="w-full p-2 border-b outline-none"
                    value={dropdownSearch}
                    onChange={(e) => setDropdownSearch(e.target.value)}
                  />
                  {frameworkOptions
                    .filter(opt => opt.toLowerCase().includes(dropdownSearch.toLowerCase()))
                    .map(opt => (
                      <div
                        key={opt}
                        data-testid={`option-${opt}`}
                        className="p-2 hover:bg-indigo-50 cursor-pointer"
                        onClick={() => {
                          setSelectedCustomOption(opt);
                          setCustomDropdownOpen(false);
                          setDropdownSearch('');
                        }}
                      >
                        {opt}
                      </div>
                    ))}
                </div>
              )}
            </div>

            {/* Checkbox & Radio */}
            <div className="flex gap-8">
              <div>
                <label className="block text-sm font-medium mb-2">Gender</label>
                <div className="flex gap-4">
                  {['Male', 'Female'].map(g => (
                    <label key={g} className="flex items-center gap-1">
                      <input 
                        type="radio" 
                        name="gender" 
                        value={g} 
                        data-testid={`gender-${g.toLowerCase()}`}
                        onChange={(e) => handleInputChange('gender', e.target.value)}
                      /> {g}
                    </label>
                  ))}
                </div>
              </div>

              <div>
                <label className="block text-sm font-medium mb-2">Skills (Multi-select)</label>
                <div className="flex gap-4">
                  {['Java', 'Playwright'].map(s => (
                    <label key={s} className="flex items-center gap-1">
                      <input 
                        type="checkbox" 
                        data-testid={`skill-${s.toLowerCase()}`}
                        checked={formData.skills.includes(s)}
                        onChange={() => handleCheckboxChange(s)}
                      /> {s}
                    </label>
                  ))}
                </div>
              </div>
            </div>

            <button onClick={handleSubmit} data-testid="submit-btn" className="bg-indigo-600 text-white px-6 py-2 rounded hover:bg-indigo-700">
              Submit Form
            </button>
            {alertMessage && <div data-testid="form-message" className={`p-2 rounded ${alertType === 'success' ? 'bg-green-100 text-green-800' : 'bg-red-100'}`}>{alertMessage}</div>}
          </div>
        )}

        {/* ================= TAB: INTERACTIONS (Drag & Drop) ================= */}
        {selectedTab === 'interactions' && (
          <div className="space-y-6">
            <div className="bg-white rounded-xl shadow p-8">
              <h2 className="text-xl font-bold mb-4">Drag and Drop (Checklist Item)</h2>
              <p className="text-sm text-gray-500 mb-4">Drag items from 'To Do' to 'Done'. Use `page.dragAndDrop()` or `mouse` actions.</p>
              
              <div className="flex gap-8">
                {['todo', 'done'].map(listType => (
                  <div
                    key={listType}
                    data-testid={`drop-zone-${listType}`}
                    onDragOver={onDragOver}
                    onDrop={(e) => onDrop(e, listType)}
                    className={`flex-1 p-4 rounded-lg min-h-[200px] border-2 border-dashed ${listType === 'todo' ? 'bg-orange-50 border-orange-200' : 'bg-green-50 border-green-200'}`}
                  >
                    <h3 className="font-bold uppercase text-gray-500 mb-3">{listType}</h3>
                    {dragItems.filter(item => item.list === listType).map(item => (
                      <div
                        key={item.id}
                        draggable
                        onDragStart={(e) => onDragStart(e, item.id)}
                        data-testid={`drag-item-${item.id}`}
                        className="bg-white p-3 mb-2 rounded shadow cursor-grab active:cursor-grabbing border border-gray-200 flex items-center gap-2"
                      >
                        <Move size={16} className="text-gray-400"/>
                        {item.content}
                      </div>
                    ))}
                  </div>
                ))}
              </div>
            </div>

            <div className="bg-white rounded-xl shadow p-8">
              <h2 className="text-xl font-bold mb-4">Hover State</h2>
              <div 
                className="relative inline-block"
                onMouseEnter={() => setIsHovered(true)}
                onMouseLeave={() => setIsHovered(false)}
              >
                <button data-testid="hover-btn" className="bg-gray-800 text-white px-4 py-2 rounded">
                  Hover Me
                </button>
                {isHovered && (
                  <div data-testid="hover-menu" className="absolute top-full left-0 mt-2 w-48 bg-white shadow-xl border rounded p-2 z-20">
                    <a href="#" className="block px-4 py-2 hover:bg-gray-100">Option 1</a>
                    <a href="#" className="block px-4 py-2 hover:bg-gray-100">Option 2</a>
                  </div>
                )}
              </div>
            </div>
          </div>
        )}

        {/* ================= TAB: TABLES (Pagination) ================= */}
        {selectedTab === 'tables' && (
          <div className="bg-white rounded-xl shadow p-8">
            <h2 className="text-xl font-bold mb-4">Dynamic Table with Pagination</h2>
            <p className="text-sm text-gray-500 mb-4">Test sorting, verifying cell content, deleting rows, and handling pagination.</p>

            <div className="overflow-x-auto mb-4">
              <table className="w-full text-left border-collapse" data-testid="data-table">
                <thead>
                  <tr className="bg-gray-100 border-b">
                    <th className="p-3 cursor-pointer" onClick={() => handleSort('id')}>ID</th>
                    <th className="p-3 cursor-pointer" onClick={() => handleSort('name')}>Name</th>
                    <th className="p-3 cursor-pointer" onClick={() => handleSort('salary')}>Salary</th>
                    <th className="p-3">Actions</th>
                  </tr>
                </thead>
                <tbody>
                  {currentRows.map(row => (
                    <tr key={row.id} className="border-b hover:bg-gray-50" data-testid={`row-${row.id}`}>
                      <td className="p-3">{row.id}</td>
                      <td className="p-3">{row.name}</td>
                      <td className="p-3">${row.salary}</td>
                      <td className="p-3">
                        <button 
                          onClick={() => deleteRow(row.id)}
                          data-testid={`delete-btn-${row.id}`}
                          className="text-red-500 hover:text-red-700 text-sm font-bold"
                        >
                          Delete
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>

            {/* Pagination Controls */}
            <div className="flex justify-between items-center bg-gray-50 p-3 rounded">
              <span data-testid="pagination-info">Page {currentPage} of {totalPages}</span>
              <div className="gap-2 flex">
                <button
                  onClick={() => setCurrentPage(p => Math.max(1, p - 1))}
                  disabled={currentPage === 1}
                  data-testid="prev-btn"
                  className="px-3 py-1 bg-white border rounded disabled:opacity-50"
                >
                  Previous
                </button>
                <button
                  onClick={() => setCurrentPage(p => Math.min(totalPages, p + 1))}
                  disabled={currentPage === totalPages}
                  data-testid="next-btn"
                  className="px-3 py-1 bg-white border rounded disabled:opacity-50"
                >
                  Next
                </button>
              </div>
            </div>
          </div>
        )}

        {/* ================= TAB: ASYNC / WAITS ================= */}
        {selectedTab === 'async' && (
          <div className="bg-white rounded-xl shadow p-8">
            <h2 className="text-xl font-bold mb-4">Async Loading & Timeouts</h2>
            <p className="text-sm text-gray-500 mb-4">
              Click the button. The element will appear after 3 seconds. Use `page.waitForSelector()` or `expect(locator).toBeVisible()`.
            </p>

            <button
              onClick={fetchAsyncData}
              data-testid="get-data-btn"
              className="bg-blue-600 text-white px-4 py-2 rounded disabled:opacity-50"
              disabled={isLoading}
            >
              {isLoading ? 'Loading...' : 'Get User Data (3s Delay)'}
            </button>

            {isLoading && <div className="mt-4 text-gray-500 animate-pulse">Processing request...</div>}

            {asyncData && (
              <div data-testid="async-content" className="mt-4 p-4 bg-green-50 border border-green-200 rounded text-green-800">
                <h3 className="font-bold">Success!</h3>
                <p>{asyncData.message}</p>
              </div>
            )}
          </div>
        )}

        {/* ================= TAB: ADVANCED (Popups/File) ================= */}
        {selectedTab === 'advanced' && (
          <div className="space-y-6">
            <div className="bg-white rounded-xl shadow p-8">
              <h2 className="text-xl font-bold mb-4">Dialogs & New Windows</h2>
              <div className="flex gap-4">
                <button onClick={showConfirm} data-testid="confirm-btn" className="bg-yellow-500 text-white px-4 py-2 rounded">
                  Trigger Confirm
                </button>
                <button onClick={showPrompt} data-testid="prompt-btn" className="bg-purple-500 text-white px-4 py-2 rounded">
                  Trigger Prompt
                </button>
              </div>
              <p className="mt-2 text-sm text-gray-500">Result: <span id="dialog-result">{alertMessage}</span></p>
            </div>

            <div className="bg-white rounded-xl shadow p-8">
              <h2 className="text-xl font-bold mb-4">Files: Upload & Download</h2>
              <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
                <div className="border p-4 rounded bg-gray-50">
                  <h3 className="font-bold mb-2">Upload</h3>
                  <input type="file" data-testid="file-input" onChange={handleFileUpload} />
                  {uploadedFile && <p className="mt-2 text-green-600">Selected: {uploadedFile}</p>}
                </div>
                <div className="border p-4 rounded bg-gray-50">
                  <h3 className="font-bold mb-2">Download</h3>
                  <button onClick={handleDownload} data-testid="download-btn" className="flex items-center gap-2 bg-gray-800 text-white px-4 py-2 rounded">
                    <Download size={16}/> Download JSON
                  </button>
                </div>
              </div>
            </div>

            <div className="bg-white rounded-xl shadow p-8">
              <h2 className="text-xl font-bold mb-4">Iframe Interaction</h2>
              <iframe
  srcDoc={`
    <html style='height:100%'>
      <body style='display:flex;justify-content:center;align-items:center;background:#f0f9ff;height:100%;margin:0;'>
        <div>
          <h2>I am inside an Iframe</h2>
          <button 
            id='iframe-btn' 
            style='background:red;color:white;padding:10px;border:none;cursor:pointer' 
            onclick='alert("Clicked inside iframe")'
          >
            Click Me
          </button>
        </div>
      </body>
    </html>
  `}
  title="test-iframe"
  data-testid="iframe-content"
  className="w-full h-48 border rounded"
></iframe>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}