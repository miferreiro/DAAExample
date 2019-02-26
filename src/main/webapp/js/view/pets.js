var PetsView = (function() {
	var dao;

	// Referencia a this que permite acceder a las funciones públicas desde las funciones de jQuery.
	var self;
	
	var formId = 'pets-form';
	var listId = 'pets-list';
	var formQuery = '#' + formId;
	var listQuery = '#' + listId;
	
	function PetsView(petsDao, formContainerId, listContainerId) {
		dao = petsDao;
		self = this;
		
		insertPetsForm($('#' + formContainerId));
		insertPetsList($('#' + listContainerId));
		
		this.init = function() {
			$('#' + formContainerId).before('<h1 class="display-5 mt-3 mb-3" id="pets-tittle">Mascotas</h1>');
			dao.listPets(function(pets) {
				$.each(pets, function(key, pet) {
					
					var daoPeople = new PeopleDAO();
					
					daoPeople.getPeople(pet.idOwner,
					 				    function(person) {
												appendToTable(pet, person);
										},
										showErrorMessage
										);
				});
			});
			
			// La acción por defecto de enviar formulario (submit) se sobreescribe
			// para que el envío sea a través de AJAX
			$(formQuery).submit(function(event) {	
				var pet = self.getPetInForm();
						
				if (self.isEditing()) {		
					dao.modifyPet(pet,	
						function(pet) {
							$('#pet-' + pet.id + ' td.namePet').text(pet.name);
							$('#pet-' + pet.id + ' td.specie').text(pet.specie);
							$('#pet-' + pet.id + ' td.idOwner').text(pet.idOwner);
							self.resetForm();
						},
						showErrorMessage,
						self.enableForm
					);
					
				} else {
					dao.addPet(pet,
						function(pet) {
							appendToTable(pet);
							self.resetForm();
						},
						showErrorMessage,
						self.enableForm
					);
				}
				
				return false;
			});
			
			$('#btnClearPet').click(this.resetForm);
		};
		
		this.initPetsPerson = function(id, person) {
			
			$('#' + formContainerId).before('<h1 class="display-5 mt-3 mb-3" id="pets-tittle">Mascotas de ' + person.name + ' ' + person.surname + '</h1>');
			
			$('#person-name-th').remove();
			$('#person-surname-th').remove();
			
			dao.listPeoplePets(id,
							   function(pets) {
								$.each(pets, function(key, pet) {
									appendToTablePetsPerson(pet);
								});
			});
			
			// La acción por defecto de enviar formulario (submit) se sobreescribe
			// para que el envío sea a través de AJAX
			$(formQuery).submit(function(event) {	
				var pet = self.getPetInForm();
						
				if (self.isEditing()) {		
					dao.modifyPet(pet,	
						function(pet) {
							$('#pet-' + pet.id + ' td.namePet').text(pet.name);
							$('#pet-' + pet.id + ' td.specie').text(pet.specie);
							$('#pet-' + pet.id + ' td.idOwner').text(pet.idOwner);
							self.resetForm();
							if(pet.idOwner != id) {
								$('tr#pet-' + pet.id).remove();
							}
						},
						showErrorMessage,
						self.enableForm
					);
					
				} else {
					dao.addPet(pet,
						function(pet) {
							appendToTable(pet);
							self.resetForm();
							if(pet.idOwner != id) {
								$('tr#pet-' + pet.id).remove();
							}
						},
						showErrorMessage,
						self.enableForm
					);
				}
				
				return false;
			});
			
			$('#btnClearPet').click(this.resetForm);
		};
		
		
		this.getPetInForm = function() {
			var form = $(formQuery);
			return {
				'id': form.find('input[name="idPet"]').val(),
				'name': form.find('input[name="namePet"]').val(),
				'specie': form.find('input[name="specie"]').val(),
				'idOwner': form.find('input[name="idOwner"]').val()
			};
		};

		this.getPetInRow = function(id) {
			var row = $('#pet-' + id);

			if (row !== undefined) {
				return {
					'id': id,
					'name': row.find('td.namePet').text(),
					'specie': row.find('td.specie').text(),
					'idOwner': form.find('td.idOwner').text()
				};
			} else {
				return undefined;
			}
		};
		
		this.editPet = function(id) {
			var row = $('#pet-' + id);

			if (row !== undefined) {
				var form = $(formQuery);
				
				form.find('input[name="idPet"]').val(id);
				form.find('input[name="namePet"]').val(row.find('td.namePet').text());
				form.find('input[name="specie"]').val(row.find('td.specie').text());
				form.find('input[name="idOwner"]').val(row.find('td.idOwner').text());
				
				$('input#btnSubmitPet').val('Modificar');
			}
		};
		
		this.deletePet = function(id) {
			if (confirm('Está a punto de eliminar a una mascota. ¿Está seguro de que desea continuar?')) {
				dao.deletePet(id,
					function() {
						$('tr#pet-' + id).remove();
						self.resetForm();
					},
					showErrorMessage
				);
			}
		};

		this.isEditing = function() {
			return $(formQuery + ' input[name="idPet"]').val() != "";
		};

		this.disableForm = function() {
			$(formQuery + ' input').prop('disabled', true);
		};

		this.enableForm = function() {
			$(formQuery + ' input').prop('disabled', false);
		};

		this.resetForm = function() {
			$(formQuery)[0].reset();
			$(formQuery + ' input[name="idPet"]').val('');
			$('#btnSubmitPet').val('Crear');
		};
	};
	
	var insertPetsList = function(parent) {
		parent.append(
			'<table id="' + listId + '" class="table">\
				<thead>\
					<tr class="row">\
						<th class="col-sm-2" style="text-align:center;">Nombre</th>\
						<th class="col-sm-2" style="text-align:center;">Especie</th>\
						<th class="col-sm-2" style="text-align:center;" id="person-name-th">Nombre Propietario</th>\
						<th class="col-sm-2" style="text-align:center;" id="person-surname-th">Apellido Propietario</th>\
						<th class="col-sm-3">&nbsp;</th>\
					</tr>\
				</thead>\
				<tbody>\
				</tbody>\
			</table>'
		);
	};

	var insertPetsForm = function(parent) {
		parent.append(
			'<form id="' + formId + '" class="mb-5 mb-11">\
				<input name="idPet" type="hidden" value=""/>\
				<div class="row">\
					<div class="col-sm-3">\
						<input name="namePet" type="text" value="" placeholder="Nombre" class="form-control" required/>\
					</div>\
					<div class="col-sm-3">\
						<input name="specie" type="text" value="" placeholder="Especie" class="form-control" required/>\
					</div>\
					<div class="col-sm-3">\
						<input name="idOwner" type="number" value="" placeholder="Propietario" class="form-control" required/>\
					</div>\
					<div class="col-sm-3">\
						<input id="btnSubmitPet" type="submit" value="Crear" class="btn btn-primary" />\
						<input id="btnClearPet" type="reset" value="Limpiar" class="btn" />\
					</div>\
				</div>\
			</form>'
		);
	};

	var createPetRow = function(pet, person) {
				
		return '<tr id="pet-'+ pet.id +'" class="row">\
			<td class="namePet col-sm-2" style="text-align:center;">' + pet.name + '</td>\
			<td class="specie col-sm-2" style="text-align:center;">' + pet.specie + '</td>\
			<td style="display:none;" class="idOwner col-sm-3" style="text-align:center;">' + pet.idOwner + '</td>\
			<td class="person-name col-sm-2" style="text-align:center;">' + person.name + '</td>\
			<td class="person-surname col-sm-2" style="text-align:center;">' + person.surname + '</td>\
			<td class="col-sm-3">\
				<a class="edit btn btn-primary" href="#">Editar</a>\
				<a class="delete btn btn-warning" href="#">Eliminar</a>\
			</td>\
		</tr>';
	};

	var createPetRowPetsPerson = function(pet) {
				
		return '<tr id="pet-'+ pet.id +'" class="row">\
			<td class="namePet col-sm-2" style="text-align:center;">' + pet.name + '</td>\
			<td class="specie col-sm-2" style="text-align:center;">' + pet.specie + '</td>\
			<td style="display:none;" class="idOwner col-sm-3" style="text-align:center;">' + pet.idOwner + '</td>\
			<td class="col-sm-3">\
				<a class="edit btn btn-primary" href="#">Editar</a>\
				<a class="delete btn btn-warning" href="#">Eliminar</a>\
			</td>\
		</tr>';
	};

	
	var showErrorMessage = function(jqxhr, textStatus, error) {
		alert(textStatus + ": " + error);
	};

	var addRowListeners = function(pet) {
		$('#pet-' + pet.id + ' a.edit').click(function() {
			self.editPet(pet.id);
		});
		
		$('#pet-' + pet.id + ' a.delete').click(function() {
			self.deletePet(pet.id);
		});
	};

	var appendToTable = function(pet, person) {
		$(listQuery + ' > tbody:last')
			.append(createPetRow(pet, person));
		addRowListeners(pet);
	};
	
	var appendToTablePetsPerson = function(pet) {
		$(listQuery + ' > tbody:last')
			.append(createPetRowPetsPerson(pet));
		addRowListeners(pet);
	};
	
	return PetsView;
})();
