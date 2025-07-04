<%-- 
    Document   : mensajes
    Created on : 26 jun. 2025, 10:52:20
    Author     : Juan - Luis
--%>
<!-- Modales de mensajes interactivos -->
<!-- Modal de exito -->
<div id="successModal" class="dialog hidden">
    <div class="dialog-content dialog-success">
        <div class="flex flex-col items-center justify-center gap-4 py-6">
            <div class="flex h-12 w-12 items-center justify-center rounded-full">
                <svg class="icon-check h-8 w-8 text-green-500" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <circle cx="12" cy="12" r="10" />
                    <path d="m9 12 2 2 4-4" />
                </svg>
            </div>
            <div class="space-y-1 text-center">
                <h2 class="dialog-title text-lg font-medium">�xito</h2>
                <p class="dialog-description">La operaci�n se ha completado correctamente.</p>
            </div>
        </div>
        <div class="dialog-footer">
            <button id="closeSuccessModal" type="button" class="button-primary">Cerrar</button>
        </div>
    </div>
</div>

<!-- Modal de error -->
<div id="errorModal" class="dialog hidden">
    <div class="dialog-content dialog-error">
        <div class="flex flex-col items-center justify-center gap-4 py-6">
            <div class="flex h-12 w-12 items-center justify-center rounded-full">
                <svg class="icon-alert h-8 w-8 text-red-500" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <circle cx="12" cy="12" r="10" />
                    <line x1="12" x2="12" y1="8" y2="12" />
                    <line x1="12" x2="12.01" y1="16" y2="16" />
                </svg>
            </div>
            <div class="space-y-1 text-center">
                <h2 class="dialog-title text-lg font-medium">Error</h2>
                <p class="dialog-description">Hubo un error en la operaci�n.</p>
            </div>
        </div>
        <div class="dialog-footer">
            <button id="closeErrorModal" type="button" class="button-primary">Cerrar</button>
        </div>
    </div>
</div>

<!-- Modal de Prevenci�n -->
<div id="warningModal" class="dialog hidden">
    <div class="dialog-content dialog-warning">
        <div class="flex flex-col items-center justify-center gap-4 py-6">
            <div class="flex h-12 w-12 items-center justify-center rounded-full">
                <svg class="icon-warning h-8 w-8 text-yellow-500" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <circle cx="12" cy="12" r="10" />
                    <line x1="12" x2="12" y1="8" y2="12" />
                    <line x1="12" x2="12" y1="16" y2="16" />
                </svg>
            </div>
            <div class="space-y-1 text-center">
                <h2 class="dialog-title text-lg font-medium">Advertencia</h2>
                <p class="dialog-description">Esta es una advertencia sobre la operaci�n.</p>
            </div>
        </div>
        <div class="dialog-footer">
            <button id="closeWarningModal" type="button" class="button-primary">Cerrar</button>
        </div>
    </div>
</div>