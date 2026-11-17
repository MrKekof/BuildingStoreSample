// Base JavaScript for Admin UI

document.addEventListener('DOMContentLoaded', function() {
    // Sidebar toggle
    const sidebarCollapse = document.getElementById('sidebarCollapse');
    const sidebar = document.getElementById('sidebar');
    
    if (sidebarCollapse) {
        sidebarCollapse.addEventListener('click', function() {
            sidebar.classList.toggle('active');
        });
    }

    // Set active nav link based on current path
    const currentPath = window.location.pathname;
    const navLinks = document.querySelectorAll('#sidebar ul li a');
    navLinks.forEach(link => {
        if (link.getAttribute('href') === currentPath) {
            link.parentElement.classList.add('active');
        } else {
            link.parentElement.classList.remove('active');
        }
    });

    // Close sidebar on mobile when clicking outside
    document.addEventListener('click', function(event) {
        if (window.innerWidth < 992 && sidebar && sidebarCollapse) {
            const isClickInsideSidebar = sidebar.contains(event.target);
            const isClickOnToggle = sidebarCollapse.contains(event.target);
            
            if (!isClickInsideSidebar && !isClickOnToggle && sidebar.classList.contains('active')) {
                sidebar.classList.remove('active');
            }
        }
    });
});

/**
 * Universal API Fetch utility
 * @param {string} url - API Endpoint
 * @param {string} method - HTTP Method (GET, POST, PUT, PATCH, DELETE)
 * @param {object} data - Payload body (for POST, PUT, PATCH)
 * @returns Promise with parsed JSON response
 */
async function fetchAPI(url, method = 'GET', data = null) {
    const options = {
        method: method,
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    };

    if (data && (method === 'POST' || method === 'PUT' || method === 'PATCH')) {
        options.body = JSON.stringify(data);
    }

    try {
        const response = await fetch(url, options);
        
        // Handle 204 No Content (DELETE)
        if (response.status === 204) {
            return { success: true };
        }

        const result = await response.json();
        
        if (!response.ok) {
            const validationDetails = result.errors ? Object.values(result.errors).join('; ') : '';
            throw new Error(validationDetails || result.message || result.error || `HTTP Error ${response.status}`);
        }
        
        return result;
    } catch (error) {
        console.error(`API Error (${method} ${url}):`, error);
        throw error;
    }
}

/**
 * Show a success notification
 * @param {string} message 
 */
function showSuccessToast(message) {
    Swal.fire({
        toast: true,
        position: 'top-end',
        icon: 'success',
        title: message,
        showConfirmButton: false,
        timer: 3000,
        timerProgressBar: true,
        background: '#1e293b',
        color: '#f8fafc',
        iconColor: '#10b981'
    });
}

/**
 * Show an error notification
 * @param {string} message 
 */
function showErrorToast(message) {
    Swal.fire({
        toast: true,
        position: 'top-end',
        icon: 'error',
        title: message || 'Something went wrong!',
        showConfirmButton: false,
        timer: 4000,
        timerProgressBar: true,
        background: '#1e293b',
        color: '#f8fafc',
        iconColor: '#ef4444'
    });
}

/**
 * Show a confirmation dialog before deleting an item
 * @param {string} itemName Name of the item to delete (for display)
 * @returns Promise resolving to true if confirmed
 */
async function confirmDelete(itemName = 'this item') {
    const result = await Swal.fire({
        title: 'Are you sure?',
        text: `You won't be able to revert this! Delete ${itemName}?`,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#ef4444',
        cancelButtonColor: '#3b82f6',
        confirmButtonText: 'Yes, delete it!',
        background: '#1e293b',
        color: '#f8fafc'
    });
    
    return result.isConfirmed;
}

/**
 * Generalized CRUD generic actions
 */
const AdminAPI = {
    // GET pagination helper
    buildQueryString: function(params) {
        const urlParams = new URLSearchParams();
        if (params.page !== undefined) urlParams.append('page', params.page);
        if (params.size !== undefined) urlParams.append('size', params.size);
        if (params.sort !== undefined) urlParams.append('sort', params.sort);
        
        // Append other specific search parameters
        Object.keys(params).forEach(key => {
            if (key !== 'page' && key !== 'size' && key !== 'sort' && params[key] !== undefined && params[key] !== null && params[key] !== '') {
                 urlParams.append(key, params[key]);
            }
        });
        
        const qs = urlParams.toString();
        return qs ? `?${qs}` : '';
    },
    
    // Pagination rendering utility
    renderPagination: function(pageData, loadFunction) {
        if (!pageData || pageData.totalPages <= 1) return '';
        
        let html = `<nav aria-label="Page navigation"><ul class="pagination pagination-sm justify-content-end">`;
        
        // Previous button
        const prevDisabled = pageData.first ? 'disabled' : '';
        html += `<li class="page-item ${prevDisabled}">
                    <a class="page-link" href="#" data-page="${pageData.number - 1}">Previous</a>
                 </li>`;
                 
        // Page numbers
        for (let i = 0; i < pageData.totalPages; i++) {
            const active = i === pageData.number ? 'active' : '';
            html += `<li class="page-item ${active}">
                        <a class="page-link" href="#" data-page="${i}">${i + 1}</a>
                     </li>`;
        }
        
        // Next button
        const nextDisabled = pageData.last ? 'disabled' : '';
        html += `<li class="page-item ${nextDisabled}">
                    <a class="page-link" href="#" data-page="${pageData.number + 1}">Next</a>
                 </li>`;
                 
        html += `</ul></nav>`;
        return html;
    }
};
