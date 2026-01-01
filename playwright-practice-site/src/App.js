import React, { useState } from 'react';
import { Download, CheckSquare, Table, BarChart3, Database } from 'lucide-react';

export default function PlaywrightTestSite() {
  const [selectedTab, setSelectedTab] = useState('basics');
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    country: '',
    gender: '',
    skills: [],
    dateOfBirth: ''
  });
  const [errors, setErrors] = useState({});
  const [tableData, setTableData] = useState([
    { id: 1, name: 'Alice Johnson', role: 'Developer', salary: 95000, status: 'Active' },
    { id: 2, name: 'Bob Smith', role: 'Designer', salary: 85000, status: 'Active' },
    { id: 3, name: 'Carol White', role: 'Manager', salary: 110000, status: 'Inactive' },
    { id: 4, name: 'David Brown', role: 'Tester', salary: 75000, status: 'Active' }
  ]);
  const [sortConfig, setSortConfig] = useState({ key: null, direction: 'asc' });
  const [alertMessage, setAlertMessage] = useState('');
  const [alertType, setAlertType] = useState('success');
  const [uploadedFile, setUploadedFile] = useState(null);
  const [customDropdownOpen, setCustomDropdownOpen] = useState(false);
  const [selectedCustomOption, setSelectedCustomOption] = useState('Select Framework');

  const validateForm = () => {
    const newErrors = {};

    // Username validation
    if (!formData.username.trim()) {
      newErrors.username = 'Username is required';
    } else if (formData.username.length < 3) {
      newErrors.username = 'Username must be at least 3 characters';
    } else if (formData.username.length > 20) {
      newErrors.username = 'Username must not exceed 20 characters';
    } else if (!/^[a-zA-Z0-9_]+$/.test(formData.username)) {
      newErrors.username = 'Username can only contain letters, numbers, and underscore';
    }

    // Email validation
    if (!formData.email.trim()) {
      newErrors.email = 'Email is required';
    } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.email)) {
      newErrors.email = 'Please enter a valid email address';
    }

    // Country validation
    if (!formData.country) {
      newErrors.country = 'Please select a country';
    }

    // Gender validation
    if (!formData.gender) {
      newErrors.gender = 'Please select your gender';
    }

    // Skills validation
    if (formData.skills.length === 0) {
      newErrors.skills = 'Please select at least one skill';
    }

    // Date of birth validation
    if (!formData.dateOfBirth) {
      newErrors.dateOfBirth = 'Date of birth is required';
    } else {
      const birthDate = new Date(formData.dateOfBirth);
      const today = new Date();
      const age = today.getFullYear() - birthDate.getFullYear();
      
      if (birthDate > today) {
        newErrors.dateOfBirth = 'Date of birth cannot be in the future';
      } else if (age < 18) {
        newErrors.dateOfBirth = 'You must be at least 18 years old';
      } else if (age > 100) {
        newErrors.dateOfBirth = 'Please enter a valid date of birth';
      }
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = () => {
    if (validateForm()) {
      setAlertMessage('Form submitted successfully! All validations passed.');
      setAlertType('success');
      // Reset form after successful submission
      setTimeout(() => {
        setFormData({
          username: '',
          email: '',
          country: '',
          gender: '',
          skills: [],
          dateOfBirth: ''
        });
        setErrors({});
      }, 2000);
    } else {
      setAlertMessage('Form validation failed! Please fix the errors below.');
      setAlertType('error');
    }
  };

  const handleSort = (key) => {
    let direction = 'asc';
    if (sortConfig.key === key && sortConfig.direction === 'asc') {
      direction = 'desc';
    }
    setSortConfig({ key, direction });

    const sorted = [...tableData].sort((a, b) => {
      if (a[key] < b[key]) return direction === 'asc' ? -1 : 1;
      if (a[key] > b[key]) return direction === 'asc' ? 1 : -1;
      return 0;
    });
    setTableData(sorted);
  };

  const handleCheckboxChange = (skill) => {
    const newSkills = formData.skills.includes(skill)
      ? formData.skills.filter(s => s !== skill)
      : [...formData.skills, skill];
    
    setFormData(prev => ({ ...prev, skills: newSkills }));
    
    // Clear skills error if at least one is selected
    if (newSkills.length > 0 && errors.skills) {
      setErrors(prev => ({ ...prev, skills: undefined }));
    }
  };

  const handleInputChange = (field, value) => {
    setFormData(prev => ({ ...prev, [field]: value }));
    // Clear error for this field when user starts typing
    if (errors[field]) {
      setErrors(prev => ({ ...prev, [field]: undefined }));
    }
  };

  const handleFileUpload = (e) => {
    const file = e.target.files[0];
    if (file) {
      // Validate file size (max 5MB)
      const maxSize = 5 * 1024 * 1024;
      if (file.size > maxSize) {
        setAlertMessage(`File size exceeds 5MB limit. Selected file: ${(file.size / 1024 / 1024).toFixed(2)}MB`);
        setAlertType('error');
        setUploadedFile(null);
        return;
      }

      // Validate file type
      const allowedTypes = ['image/jpeg', 'image/png', 'image/gif', 'application/pdf', 'text/plain'];
      if (!allowedTypes.includes(file.type)) {
        setAlertMessage(`Invalid file type. Allowed: JPG, PNG, GIF, PDF, TXT`);
        setAlertType('error');
        setUploadedFile(null);
        return;
      }

      setUploadedFile(file.name);
      setAlertMessage(`File "${file.name}" uploaded successfully! Size: ${(file.size / 1024).toFixed(2)}KB`);
      setAlertType('success');
    }
  };

  const handleDownload = () => {
    const blob = new Blob(['Sample test data for Playwright automation'], { type: 'text/plain' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = 'test-data.txt';
    a.click();
    URL.revokeObjectURL(url);
  };

  const showAlert = () => {
    alert('This is a browser alert for testing!');
  };

  const showConfirm = () => {
    // eslint-disable-next-line no-restricted-globals
    if (confirm('Do you want to proceed with this action?')) {
      setAlertMessage('Confirmed! You clicked OK.');
      setAlertType('success');
    } else {
      setAlertMessage('Cancelled! You clicked Cancel.');
      setAlertType('error');
    }
  };

  const apiData = {
    users: [
      { id: 1, name: 'John Doe', email: 'john@test.com' },
      { id: 2, name: 'Jane Smith', email: 'jane@test.com' }
    ],
    status: 'success'
  };

  const tabs = [
    { id: 'basics', label: 'Form Elements', icon: CheckSquare },
    { id: 'tables', label: 'Tables', icon: Table },
    { id: 'advanced', label: 'Advanced', icon: BarChart3 },
    { id: 'api', label: 'API Testing', icon: Database }
  ];

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 p-8">
      <div className="max-w-6xl mx-auto">
        <header className="text-center mb-8">
          <h1 className="text-4xl font-bold text-indigo-900 mb-2">Playwright Test Automation Practice</h1>
          <p className="text-gray-600">Comprehensive testing scenarios with form validation</p>
        </header>

        <div className="flex gap-2 mb-6 flex-wrap">
          {tabs.map(tab => {
            const Icon = tab.icon;
            return (
              <button
                key={tab.id}
                onClick={() => setSelectedTab(tab.id)}
                className={`flex items-center gap-2 px-4 py-2 rounded-lg font-medium transition-all ${
                  selectedTab === tab.id
                    ? 'bg-indigo-600 text-white shadow-lg'
                    : 'bg-white text-gray-700 hover:bg-indigo-50'
                }`}
                data-testid={`tab-${tab.id}`}
              >
                <Icon size={18} />
                {tab.label}
              </button>
            );
          })}
        </div>

        {selectedTab === 'basics' && (
          <div className="bg-white rounded-xl shadow-lg p-8">
            <h2 className="text-2xl font-bold text-gray-800 mb-6">Form Elements with Validation</h2>
            
            <div className="space-y-6">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Username <span className="text-red-500">*</span>
                </label>
                <input
                  type="text"
                  data-testid="username-input"
                  placeholder="Enter username (3-20 chars, alphanumeric)"
                  value={formData.username}
                  onChange={(e) => handleInputChange('username', e.target.value)}
                  className={`w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent ${
                    errors.username ? 'border-red-500' : 'border-gray-300'
                  }`}
                />
                {errors.username && (
                  <p className="mt-1 text-sm text-red-600" data-testid="username-error">
                    {errors.username}
                  </p>
                )}
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Email <span className="text-red-500">*</span>
                </label>
                <input
                  type="email"
                  data-testid="email-input"
                  placeholder="Enter email (e.g., user@example.com)"
                  value={formData.email}
                  onChange={(e) => handleInputChange('email', e.target.value)}
                  className={`w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent ${
                    errors.email ? 'border-red-500' : 'border-gray-300'
                  }`}
                />
                {errors.email && (
                  <p className="mt-1 text-sm text-red-600" data-testid="email-error">
                    {errors.email}
                  </p>
                )}
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Country (Native Dropdown) <span className="text-red-500">*</span>
                </label>
                <select
                  data-testid="country-select"
                  value={formData.country}
                  onChange={(e) => handleInputChange('country', e.target.value)}
                  className={`w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent ${
                    errors.country ? 'border-red-500' : 'border-gray-300'
                  }`}
                >
                  <option value="">Select Country</option>
                  <option value="usa">United States</option>
                  <option value="uk">United Kingdom</option>
                  <option value="canada">Canada</option>
                  <option value="australia">Australia</option>
                </select>
                {errors.country && (
                  <p className="mt-1 text-sm text-red-600" data-testid="country-error">
                    {errors.country}
                  </p>
                )}
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">Custom Dropdown (Div-based)</label>
                <div className="relative">
                  <button
                    data-testid="custom-dropdown-trigger"
                    onClick={() => setCustomDropdownOpen(!customDropdownOpen)}
                    className="w-full px-4 py-2 border border-gray-300 rounded-lg text-left bg-white hover:border-indigo-500 transition-colors"
                  >
                    {selectedCustomOption}
                  </button>
                  {customDropdownOpen && (
                    <div data-testid="custom-dropdown-menu" className="absolute z-10 w-full mt-1 bg-white border border-gray-300 rounded-lg shadow-lg">
                      {['Playwright', 'Selenium', 'Cypress', 'TestCafe'].map(option => (
                        <div
                          key={option}
                          data-testid={`custom-option-${option.toLowerCase()}`}
                          onClick={() => {
                            setSelectedCustomOption(option);
                            setCustomDropdownOpen(false);
                          }}
                          className="px-4 py-2 hover:bg-indigo-50 cursor-pointer transition-colors"
                        >
                          {option}
                        </div>
                      ))}
                    </div>
                  )}
                </div>
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Gender (Radio Buttons) <span className="text-red-500">*</span>
                </label>
                <div className="flex gap-4">
                  {['male', 'female', 'other'].map(gender => (
                    <label key={gender} className="flex items-center gap-2 cursor-pointer">
                      <input
                        type="radio"
                        name="gender"
                        value={gender}
                        data-testid={`radio-${gender}`}
                        checked={formData.gender === gender}
                        onChange={(e) => handleInputChange('gender', e.target.value)}
                        className="w-4 h-4 text-indigo-600"
                      />
                      <span className="capitalize">{gender}</span>
                    </label>
                  ))}
                </div>
                {errors.gender && (
                  <p className="mt-1 text-sm text-red-600" data-testid="gender-error">
                    {errors.gender}
                  </p>
                )}
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Skills (Checkboxes) <span className="text-red-500">*</span>
                </label>
                <div className="grid grid-cols-2 gap-3">
                  {['Java', 'JavaScript', 'Python', 'Automation'].map(skill => (
                    <label key={skill} className="flex items-center gap-2 cursor-pointer">
                      <input
                        type="checkbox"
                        data-testid={`checkbox-${skill.toLowerCase()}`}
                        checked={formData.skills.includes(skill)}
                        onChange={() => handleCheckboxChange(skill)}
                        className="w-4 h-4 text-indigo-600 rounded"
                      />
                      <span>{skill}</span>
                    </label>
                  ))}
                </div>
                {errors.skills && (
                  <p className="mt-1 text-sm text-red-600" data-testid="skills-error">
                    {errors.skills}
                  </p>
                )}
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Date of Birth <span className="text-red-500">*</span>
                </label>
                <input
                  type="date"
                  data-testid="date-picker"
                  value={formData.dateOfBirth}
                  onChange={(e) => handleInputChange('dateOfBirth', e.target.value)}
                  className={`w-full px-4 py-2 border rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-transparent ${
                    errors.dateOfBirth ? 'border-red-500' : 'border-gray-300'
                  }`}
                />
                {errors.dateOfBirth && (
                  <p className="mt-1 text-sm text-red-600" data-testid="date-error">
                    {errors.dateOfBirth}
                  </p>
                )}
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  File Upload (Max 5MB, JPG/PNG/GIF/PDF/TXT only)
                </label>
                <input
                  type="file"
                  data-testid="file-upload"
                  onChange={handleFileUpload}
                  accept=".jpg,.jpeg,.png,.gif,.pdf,.txt"
                  className="w-full px-4 py-2 border border-gray-300 rounded-lg"
                />
                {uploadedFile && (
                  <p className="mt-2 text-sm text-green-600" data-testid="file-name">
                    Uploaded: {uploadedFile}
                  </p>
                )}
              </div>

              <button
                data-testid="submit-button"
                onClick={handleSubmit}
                className="w-full bg-indigo-600 text-white py-3 rounded-lg font-medium hover:bg-indigo-700 transition-colors"
              >
                Submit Form
              </button>

              {alertMessage && (
                <div 
                  data-testid="alert-message" 
                  className={`p-4 border rounded-lg ${
                    alertType === 'success' 
                      ? 'bg-green-50 border-green-200 text-green-800' 
                      : 'bg-red-50 border-red-200 text-red-800'
                  }`}
                >
                  {alertMessage}
                </div>
              )}
            </div>
          </div>
        )}

        {selectedTab === 'tables' && (
          <div className="bg-white rounded-xl shadow-lg p-8">
            <h2 className="text-2xl font-bold text-gray-800 mb-6">Dynamic Table Testing</h2>
            
            <div className="overflow-x-auto">
              <table className="w-full" data-testid="employee-table">
                <thead>
                  <tr className="bg-gray-50">
                    <th className="px-4 py-3 text-left text-sm font-medium text-gray-700 cursor-pointer hover:bg-gray-100" onClick={() => handleSort('id')} data-testid="sort-id">
                      ID {sortConfig.key === 'id' && (sortConfig.direction === 'asc' ? '↑' : '↓')}
                    </th>
                    <th className="px-4 py-3 text-left text-sm font-medium text-gray-700 cursor-pointer hover:bg-gray-100" onClick={() => handleSort('name')} data-testid="sort-name">
                      Name {sortConfig.key === 'name' && (sortConfig.direction === 'asc' ? '↑' : '↓')}
                    </th>
                    <th className="px-4 py-3 text-left text-sm font-medium text-gray-700 cursor-pointer hover:bg-gray-100" onClick={() => handleSort('role')} data-testid="sort-role">
                      Role {sortConfig.key === 'role' && (sortConfig.direction === 'asc' ? '↑' : '↓')}
                    </th>
                    <th className="px-4 py-3 text-left text-sm font-medium text-gray-700 cursor-pointer hover:bg-gray-100" onClick={() => handleSort('salary')} data-testid="sort-salary">
                      Salary {sortConfig.key === 'salary' && (sortConfig.direction === 'asc' ? '↑' : '↓')}
                    </th>
                    <th className="px-4 py-3 text-left text-sm font-medium text-gray-700">Status</th>
                  </tr>
                </thead>
                <tbody>
                  {tableData.map((row) => (
                    <tr key={row.id} className="border-t border-gray-200 hover:bg-gray-50" data-testid={`row-${row.id}`}>
                      <td className="px-4 py-3 text-sm" data-testid={`cell-id-${row.id}`}>{row.id}</td>
                      <td className="px-4 py-3 text-sm" data-testid={`cell-name-${row.id}`}>{row.name}</td>
                      <td className="px-4 py-3 text-sm" data-testid={`cell-role-${row.id}`}>{row.role}</td>
                      <td className="px-4 py-3 text-sm" data-testid={`cell-salary-${row.id}`}>${row.salary.toLocaleString()}</td>
                      <td className="px-4 py-3 text-sm">
                        <span className={`px-2 py-1 rounded-full text-xs ${row.status === 'Active' ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'}`} data-testid={`status-${row.id}`}>
                          {row.status}
                        </span>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        )}

        {selectedTab === 'advanced' && (
          <div className="space-y-6">
            <div className="bg-white rounded-xl shadow-lg p-8">
              <h2 className="text-2xl font-bold text-gray-800 mb-6">Alerts & Popups</h2>
              <div className="flex gap-4 flex-wrap">
                <button
                  data-testid="alert-button"
                  onClick={showAlert}
                  className="px-6 py-3 bg-yellow-500 text-white rounded-lg hover:bg-yellow-600 transition-colors"
                >
                  Show Alert
                </button>
                <button
                  data-testid="confirm-button"
                  onClick={showConfirm}
                  className="px-6 py-3 bg-orange-500 text-white rounded-lg hover:bg-orange-600 transition-colors"
                >
                  Show Confirm
                </button>
                <button
                  data-testid="new-window-button"
                  onClick={() => window.open('about:blank', '_blank')}
                  className="px-6 py-3 bg-purple-500 text-white rounded-lg hover:bg-purple-600 transition-colors"
                >
                  Open New Window
                </button>
              </div>
            </div>

            <div className="bg-white rounded-xl shadow-lg p-8">
              <h2 className="text-2xl font-bold text-gray-800 mb-6">File Operations</h2>
              <div className="flex gap-4">
                <button
                  data-testid="download-button"
                  onClick={handleDownload}
                  className="flex items-center gap-2 px-6 py-3 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition-colors"
                >
                  <Download size={18} />
                  Download Test File
                </button>
              </div>
            </div>

            <div className="bg-white rounded-xl shadow-lg p-8">
              <h2 className="text-2xl font-bold text-gray-800 mb-6">Iframe Testing</h2>
              <iframe
                title="Test Iframe for Playwright"
                data-testid="test-iframe"
                srcDoc="<html><body><h1>Iframe Content</h1><button id='iframe-button'>Click Me Inside Iframe</button></body></html>"
                className="w-full h-40 border border-gray-300 rounded-lg"
              />
            </div>
          </div>
        )}

        {selectedTab === 'api' && (
          <div className="bg-white rounded-xl shadow-lg p-8">
            <h2 className="text-2xl font-bold text-gray-800 mb-6">API Testing Mock Data</h2>
            <p className="text-gray-600 mb-4">Use these endpoints in your Playwright tests with APIRequestContext:</p>
            
            <div className="space-y-4">
              <div className="p-4 bg-gray-50 rounded-lg">
                <div className="flex items-center gap-2 mb-2">
                  <span className="px-2 py-1 bg-green-500 text-white text-xs rounded">GET</span>
                  <code className="text-sm">/api/users</code>
                </div>
                <pre className="text-xs bg-gray-800 text-green-400 p-3 rounded overflow-x-auto" data-testid="api-response">
                  {JSON.stringify(apiData, null, 2)}
                </pre>
              </div>

              <div className="p-4 bg-gray-50 rounded-lg">
                <div className="flex items-center gap-2 mb-2">
                  <span className="px-2 py-1 bg-blue-500 text-white text-xs rounded">POST</span>
                  <code className="text-sm">/api/users</code>
                </div>
                <p className="text-sm text-gray-600">Create new user with JSON body</p>
              </div>

              <div className="p-4 bg-gray-50 rounded-lg">
                <div className="flex items-center gap-2 mb-2">
                  <span className="px-2 py-1 bg-orange-500 text-white text-xs rounded">PUT</span>
                  <code className="text-sm">/api/users/:id</code>
                </div>
                <p className="text-sm text-gray-600">Update user by ID</p>
              </div>

              <div className="p-4 bg-gray-50 rounded-lg">
                <div className="flex items-center gap-2 mb-2">
                  <span className="px-2 py-1 bg-red-500 text-white text-xs rounded">DELETE</span>
                  <code className="text-sm">/api/users/:id</code>
                </div>
                <p className="text-sm text-gray-600">Delete user by ID</p>
              </div>
            </div>
          </div>
        )}

        <footer className="mt-8 text-center text-gray-600 text-sm">
          <p>All elements include data-testid attributes for easy Playwright selector targeting</p>
          <p className="mt-1">Form includes comprehensive validation for testing error scenarios</p>
        </footer>
      </div>
    </div>
  );
}